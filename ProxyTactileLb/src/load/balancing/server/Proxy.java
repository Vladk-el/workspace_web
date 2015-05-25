package load.balancing.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ReadOnlyBufferException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import load.balancing.server.request.Request;
import load.balancing.server.strategy.Strategy;
import load.balancing.server.strategy.round.robin.Robin;
import load.balancing.server.strategy.round.robin.StrategyRoundRobin;
import load.balancing.server.strategy.stiky.session.StikySession;
import load.balancing.server.strategy.stiky.session.StrategyStikySession;

public class Proxy {

	private Map<Integer, Map<String, String>> workers;

	private Map<Integer, Map<String, String>> loadBalancers;

	private Map<Integer, Strategy> strategies;

	private Map<String, Integer> stikies;

	public Proxy() {
		init();
		run();
	}

	public void init() {

		BufferedReader br = null;
		workers = new HashMap<Integer, Map<String, String>>();
		loadBalancers = new HashMap<Integer, Map<String, String>>();
		strategies = new HashMap<Integer, Strategy>();
		stikies = new HashMap<String, Integer>();

		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					"config.ini")));

			String s = "";

			while (null != (s = br.readLine())) {
				if (!s.startsWith("#")) {
					String key = s.split("=")[0];
					Integer id = Integer.parseInt(key.split("\\.")[1]);
					String value = s.split("=")[1];

					if (s.startsWith("worker")) {
						if (workers.get(id) == null)
							workers.put(id, new HashMap<>());

						if (key.endsWith("ip")) {
							workers.get(id).put("ip", value);
						} else if (key.endsWith("port")) {
							workers.get(id).put("port", value);
						}

					} else if (s.startsWith("lb")) {
						if (loadBalancers.get(id) == null)
							loadBalancers.put(id, new HashMap<>());

						if (key.endsWith("workers")) {
							loadBalancers.get(id).put("workers", value);
						} else if (key.endsWith("strategy")) {
							loadBalancers.get(id).put("strategy", value);

							if (strategies.get(id) == null) {
								switch (value) {
								case "round_robin":
									strategies
											.put(id, new StrategyRoundRobin());
									break;

								case "stiky_session":
									strategies.put(id,
											new StrategyStikySession());
									break;

								default:
									break;
								}
							}

						} else if (key.endsWith("domain")) {
							loadBalancers.get(id).put("domain", value);
						} else if (key.endsWith("ban_on_fail")) {
							loadBalancers.get(id).put("ban_on_fail", value);
						}

					}
				}

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		/**
		 * Strategy
		 */

		for (Integer key : strategies.keySet()) {
			if (strategies.get(key) instanceof StrategyRoundRobin) {
				List<Robin> robins = new ArrayList<>();
				List<Integer> list = new ArrayList<>();
				String[] tab = loadBalancers.get(key).get("workers").split(",");
				for (String str : tab) {
					robins.add(new Robin(Integer.parseInt(str)));
					list.add(Integer.parseInt(str));
				}
				strategies.get(key).setList(robins);
				strategies.get(key).setWorkers(list);
			} else if (strategies.get(key) instanceof StrategyStikySession) {
				List<Integer> list = new ArrayList<>();
				String[] tab = loadBalancers.get(key).get("workers").split(",");
				for (String str : tab) {
					list.add(Integer.parseInt(str));
				}
				strategies.get(key).setWorkers(list);
			}

		}

		System.out.println("workers : ");
		for (Integer key : workers.keySet()) {
			System.out.println("\t" + key + " => " + workers.get(key).get("ip")
					+ " => " + workers.get(key).get("port"));
		}

		System.out.println("loadBalancers : ");
		for (Integer key : loadBalancers.keySet()) {
			System.out.println("\t" + key + " => "
					+ loadBalancers.get(key).get("workers") + " => "
					+ loadBalancers.get(key).get("strategy"));
		}

	}

	public void run() {
		ServerSocket server = null;
		Socket client = null;

		try {

			server = new ServerSocket(80);
			System.out.println("server online, waiting for connexions...");

			while (true) {
				try {
					client = server.accept();
					System.out.println("client " + client + " connected");

					/*
					 * Request
					 */
					// System.out.println("before request");
					Request r = new Request(client);

					Integer current_lb = null;

					for (Integer key : loadBalancers.keySet()) {
						if (loadBalancers.get(key).get("domain")
								.contains(r.getHost())) {
							current_lb = key;
						}
					}

					if (current_lb != null && r.getHeader() != null) {
						// System.out.println("after request");
						if (strategies.get(current_lb) instanceof StrategyStikySession) {

							System.out.println("IS StrategyStikySession");

							StikySession ss = new StikySession(
									r.getUserAgent(), client.getInetAddress()
											.getHostAddress(), null);

							// System.out.println(ss.toString());

							((StrategyStikySession) strategies.get(current_lb))
									.setCurrent(ss);
						}

						int theGoodOne = strategies.get(current_lb)
								.getTheGoodOne();

						System.out.println("theGoodOne : " + theGoodOne);

						balance(r, theGoodOne, current_lb);

						System.out
								.println("strategies.get(current_lb).getTheGoodOne() ok");
					}

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					System.out.println("client " + client + " disconnected");
					client.close();
					System.out.println();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (server != null) {
				try {
					server.close();
					System.out.println("server offline");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void balance(Request request, Integer server, Integer loadBalancer) {
		Socket socket = null;

		try {
			socket = new Socket();
			socket.connect(new InetSocketAddress(workers.get(server).get("ip"),
					Integer.parseInt(workers.get(server).get("port"))), 1000);

			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
					socket.getOutputStream()));

			for (String str : request.getHeaders()) {
				bw.write(str + System.getProperty("line.separator"));
			}
			bw.flush();

			System.out.println("writing ok");

			BufferedReader rbr = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			BufferedWriter rbw = new BufferedWriter(new OutputStreamWriter(
					request.getSocket().getOutputStream()));

			String s = "";
			while (null != (s = rbr.readLine())) {
				rbw.write(s + System.getProperty("line.separator"));
				if (s.contains(System.getProperty("line.separator"))) {
					if (s.length() == 0) {
						break;
					}
				}
			}
			rbr.close();
			rbw.flush();

			System.out.println("reading ok");

		} catch (java.net.ConnectException | java.net.SocketTimeoutException e) {
			if (loadBalancers.get(loadBalancer).get("ban_on_fail")
					.equalsIgnoreCase("true")) {
				System.out.println("worker " + server
						+ " is now ban from the load balancer " + loadBalancer);
				strategies.get(loadBalancer).removeWorker(server); // TODO
				loadBalancers.get(loadBalancer).remove(server);
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
