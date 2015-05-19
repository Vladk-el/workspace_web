package load.balancing.server.strategy;

import java.util.Iterator;
import java.util.List;

public abstract class Strategy <T> {
	
	protected Iterator<T> it;
	
	protected List<T> list;
	
	
	public abstract int getTheGoodOne();
	
	public Iterator<T> getIt() {
		return it;
	}

	public void setIt(Iterator<T> it) {
		this.it = it;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
		it = list.iterator();
	}
	
	public void addObjectToList(T object){
		getList().add(object);
	}
}
