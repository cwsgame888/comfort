package com.core.common.map;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class DataMap<K, V> extends HashMap implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3556204360470848977L;

	/**
	 * 디폴트생성자 - initialCapacity : 500
	 */
	public DataMap() {
        super(500);
	}
	/**
	 * Map을 인자로 해서 DataMap 생성
	 * 
	 * @param map
	 */
	public DataMap(Map map) {
		super(map);
	}

	/**
	 * DataMap에서 key에 해당하는 value 값을 얻어온다.
	 * 
	 * @param name DataMap에서 가져올 key값
	 * @return key에 해당하는 value Object
	 */
	public Object get(String name) {
		Object obj = super.get(name);
		return obj;
	}

	public void put(String name, int value) {
		super.put(name, new Integer(value));
	}

	public void put(String name, long value) {
		super.put(name, new Long(value));
	}

	public void put(String name, float value) {
		super.put(name, new Float(value));
	}

	public void put(String name, double value) {
		super.put(name, new Double(value));
	}

	public void put(String name, boolean value) {
		super.put(name, "" + value);
	}

	/**
	 * DataMap에서 key에 해당하는 Value값을 String으로 얻어온다.
	 * 
	 * @param paramName DataMap에서 가져올 key값
	 * @return key에 해당하는 value String
	 * @throws Exception
	 */
	public String getString(String paramName) throws Exception {
		Object obj = super.get(paramName);
		if (obj == null) {
			return null;
		} else if (obj instanceof Collection || obj instanceof Object[]) {
			throw new Exception(paramName + "으로 등록된 객체는 String 변환 가능 객체 아님:" + obj.getClass().getName());
		} else {
			return obj.toString();
		}
	}

	/**
	 * DataMap에서 가져 온다.
	 * 
	 * @param paramName    DataMap에서 가져올 Key값
	 * @param defaultValue paramName이 null일때 설정할 기본값
	 * @return DataMap에서 Key값으로 가져온 Value
	 * @throws Exception
	 */
	public String getString(String paramName, String defaultValue) throws Exception {
		String str = getString(paramName);
		if (str == null || str.length() == 0) {
			return defaultValue;
		} else {
			return (String) str;
		}
	}

	/**
	 * DataMap에서 key에 해당하는 Value값을 Int로 얻어온다.
	 * 
	 * @param paramName DataMap에서 가져올 key값
	 * @return 0 if value is null, else return the value
	 * @throws NumberFormatException value 값이 Integer 타입이 아닐경우
	 */
	public int getInt(String paramName) throws Exception {
		String value = getString(paramName);
		if (value == null) {
			return 0;
		}
		return Integer.parseInt(value);
	}

	/**
	 * DataMap에서 int 형태로 가져 온다.
	 * 
	 * @param paramName    가져올 Key값
	 * @param defaultValue Key로 가져온 Value가 null일경우
	 * @return DataMap에서 Key값으로 가져온 int형 value값
	 */
	public int getInt(String paramName, int defaultValue) throws Exception {
		String str = getString(paramName);
		if (str == null || str.trim().length() == 0) {
			return defaultValue;
		}
		return Integer.parseInt(str);
	}

	/**
	 * DataMap에서 key에 해당하는 Value값을 Long으로 얻어온다.
	 * 
	 * @param paramName DataMap에서 가져올 key값
	 * @return key에 해당하는 value Long
	 * @throws NumberFormatException value 값이 Long 타입이 아닐경우
	 */
	public long getLong(String paramName) throws Exception {
		String value = getString(paramName);
		if (value == null) {
			return 0;
		}
		return Long.parseLong(value);
	}

	/**
	 * DataMap에서 key에 해당하는 Value값을 Long으로 얻어온다.
	 * 
	 * @param paramName    DataMap에서 가져올 key값
	 * @param defaultValue Key로 가져온 Value가 null일경우
	 * @return key에 해당하는 value Long
	 */
	public long getLong(String paramName, long defaultValue) throws Exception {
		String str = getString(paramName);
		if (str == null || str.trim().length() == 0) {
			return defaultValue;
		}
		return Long.parseLong(str);
	}

	/**
	 * DataMap에서 가져 온다.
	 * 
	 * @param DataMap에서 가져올 Key값
	 * @return DataMap에서 Key로 가져온 doble형 value
	 * @throws ParamException 값이 없을 경우 (개발자가 매번 null check를 하지 않도록 하기 위해)
	 */
	public double getDouble(String paramName) throws Exception {
		String str = getString(paramName);
		if (str == null) {
			return 0;
		}
		return Double.parseDouble(str);
	}

	/**
	 * DataMap에서 가져 온다.
	 * 
	 * @param DataMap에서 가져올 Key값
	 * @return DataMap에서 Key로 가져온 doble형 value
	 * @throws ParamException 값이 없을 경우 (개발자가 매번 null check를 하지 않도록 하기 위해)
	 */
	public double getDouble(String paramName, double defaultValue) throws Exception {
		String str = getString(paramName);
		if (str == null || str.trim().length() == 0) {
			return defaultValue;
		}
		return Double.parseDouble(str);
	}

	/**
	 * y,Y,Yes,yes,T,true,True,On,on모두 true를 리턴한다. 값이 없거나, 위의 값이 아니면 false를 리턴한다.
	 * 
	 * @param paramName
	 * @return
	 * @throws Exception
	 */
	public boolean getBoolean(String paramName) throws Exception {
		String value = getString(paramName);
		if (value == null) {
			return false;
		}
		return getBoolean(value, false);
	}

	public boolean getBoolean(String value, boolean defaultValue) {
		if (value == null)
			return defaultValue;
		if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("on") || value.equalsIgnoreCase("y")
				|| value.equalsIgnoreCase("t") || value.equalsIgnoreCase("yes") || value.equalsIgnoreCase("1"))
			return true;
		return false;
	}

	/**
	 * DataMap에서 배열 형태로 가져 온다. 값이 없으면 에러가 발생하지 않고 null을 리턴한다.
	 * 
	 * @param paramName 가져올 Key값
	 * @return DataMap에 DataMap에서 배열로 저장된 Value
	 * @throws Exception
	 */
	public String[] getParameterValues(String paramName) throws Exception {
		String[] strArr = getStringArray(paramName);
		return strArr;
	}

	/**
	 * 설명: DataMap안에 있는 객체를 array로 얻어 오는 로직.
	 * 
	 * @param paramName
	 * @return
	 * @throws Exception
	 *
	 */
	public String[] getStringArray(String paramName) throws Exception {
		Object obj = super.get(paramName);
		String param = null;
		String[] params = null;
		if (obj instanceof String[]) {
			return (String[]) obj;
		} else if (obj instanceof Collection) {
			// collection 인 경우 null리턴.
			return null;
		} else if (obj instanceof Object[]) {
			Object[] objArray = (Object[]) obj;
			String[] array = new String[objArray.length];
			for (int i = 0; i < objArray.length; i++) {
				try {
					array[i] = objArray[i].toString();
				} catch (NullPointerException e) {
					array[i] = "";
				}
			}
			return array;
		} else if (obj != null) {
			String[] array = new String[1];
			array[0] = obj.toString();
			return array;
		} else {
			param = getString(paramName);
			if (param == null) {
				return null;
			} else {
				params = new String[1];
				params[0] = param;
				return params;
			}
		}
	}

	/**
	 * debugging을 위한 메소드 추가
	 */
	public String toString() {
		if (isEmpty()) {
			return "DataMap is empty =========================";
		}
		StringBuffer buf = new StringBuffer(2000);
		Set keySet = super.keySet();
		Iterator i = keySet.iterator();
		String key = null;
		Object item = null;
		while (i.hasNext()) {
			try {
				key = i.next().toString();
				if ("q".equals(key) || "p".equals(key))
					continue;
				item = get(key);
				if (item == null) {
					buf.append(key + "=null\n");
				} else if (item instanceof String) {
					if (item == null || ((String) item).length() == 0) {
						item = "";
					}
					buf.append(key + "=[" + item + "]\n");
				} else if (item instanceof Integer || item instanceof Long || item instanceof Double
						|| item instanceof java.lang.Float || item instanceof Boolean)
					buf.append(key + "=[" + item + "]\n");
				else if (item instanceof String[]) {
					String data[] = (String[]) item;
					buf.append(key + "=[");
					int j;
					for (j = 0; j < data.length; j++) {
						buf.append(data[j]);
						if (j < (data.length - 1)) {
							buf.append(",");
						}
					}

					buf.append("] Array Size:" + j + " \n");
				} else {
					buf.append(key + "=[" + item + "] ClassName:" + item.getClass().getName() + "\n");
				}
			} catch (Exception ignore) {
			}
		}
		buf.append("end of DataMap info ============================\n");
		return buf.toString();
	}
}