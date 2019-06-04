package keelfy.klibrary.server.commands;

import java.lang.reflect.Method;
import java.util.*;

/**
 * @author keelfy
 * @date 26 мая 2019 г.
 */
public class KCommandChilds {

	private KCommandInfo info;
	private Object object;
	private Method method;
	private List<KCommandChilds> childs;

	public KCommandChilds(KCommandInfo info, Object object, Method method, List<KCommandChilds> childs) {
		this.info = info;
		this.object = object;
		this.method = method;
		this.childs = childs;
	}

	public KCommandChilds(KCommandInfo info, Object object, Method method) {
		this(info, object, method, new ArrayList<KCommandChilds>());
	}

	public void setChilds(List<KCommandChilds> childs) {
		this.childs = childs;
	}

	public List<KCommandChilds> getChilds() {
		return childs;
	}

	public void addChild(KCommandChilds child) {
		this.childs.add(child);
	}

	public KCommandInfo getInfo() {
		return info;
	}

	public Method getMethod() {
		return method;
	}

	public Object getObject() {
		return object;
	}
}
