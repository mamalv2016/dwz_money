package common.taglib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import common.util.JsonUtil;

public class DwzTreeUtil {
	// һ�����ڸ��׽ڵ�����к����ǵļ�ֵ��
	private HashMap parentMap = new HashMap();
	// һ�����ڽڵ�id�ͽڵ����ļ�ֵ��
	private HashMap nodeMap = new HashMap();
	// �������нڵ��list
	private ArrayList nodeList = new ArrayList();

	/**
	 * ������ڵ�.
	 * 
	 * @param node
	 */
	public void addNode(TreeNode node) {
		nodeList.add(node);
		nodeMap.put(node.getId(), node);
		addParentMap(node.getParent(), node);
	}

	// ��װ��һ���ڵ��븸��id��map
	private void addParentMap(String parentId, TreeNode node) {
		List list = new ArrayList();
		// ������id����ĺ�����
		if (!parentMap.containsKey(parentId)) {
			list.add(node);
			parentMap.put(parentId, list);
		} else {
			list = (ArrayList) parentMap.get(parentId);
			list.add(node);
			parentMap.put(parentId, list);
		}
	}

	// ���ݽڵ�id�õ��ڵ�
	private TreeNode getNode(String id) {
		return (TreeNode) nodeMap.get(id);
	}

	/**
	 * ����ȫ���ĸ��׽ڵ�id��ɵ�һ��set.
	 * 
	 * @return
	 */
	public Set getParentSet() {
		Set parentSet = new HashSet();
		Iterator it = nodeList.iterator();
		while (it.hasNext()) {
			TreeNode vo = (TreeNode) it.next();
			parentSet.add(vo.getParent());
		}
		return parentSet;
	}

	/**
	 * ���Һ���.
	 * 
	 * @param parentId
	 * @return
	 */
	public ArrayList getNodesByParentId(String parentId) {
		return (ArrayList) parentMap.get(parentId);
	}
	
	/**
	 * ���ؽڵ����
	 * @param id
	 * @return
	 */
	public TreeNode getNodeById(String id) {
		return (TreeNode) nodeMap.get(id);
	}

	/**
	 * ����ָ���ڵ��Ƿ��Ǹ��׽ڵ�.
	 * 
	 * @param nodeId
	 * @return
	 */
	public boolean isParent(String nodeId) {
		return parentMap.get(nodeId) == null ? false : true;
	}

	/**
	 * ���õ�һ��ڵ㡣 ��Ҫ����Щ�Ҳ������׵Ľڵ������ķ��ڸ��ڵ����档
	 * 
	 * @param root
	 */
	private void setFistFloor(TreeNode root) {
		Set parentSet = this.getParentSet();
		String rootId = root.getId();
		// ������Ų����ڵĸ��׽ڵ��list
		ArrayList notExitslist = new ArrayList();
		// ����������еĸ��ڵ��ַ�������������ڱ���Ӧ��׼������Щ���׵ĺ���ת�浽��������棬��Ϊ����ĺ���
		Object[] parents = parentSet.toArray();
		for (int temp = 0; temp < parents.length; temp++) {
			//tempId����Ϊnull,����Ҳ˵����hashMap��key����Ϊnull����
			String tempId = (String)parents[temp];
			// �����ҵ����еĲ����ڵĸ��׽ڵ㣬����list���档
			if (this.getNode(tempId) == null && !root.getId().equals(tempId)) {
				notExitslist.add((String)parents[temp]);
			}
		}
		// ������ݸ��׺���map�ҵ����е�û���ҵ����׽ڵ�ĺ����ǣ������Ƿŵ���������档��������ǵ��ж�����׽ڵ㲻���ڵ�ʱ����и��ĸ��׵��������Ĳ�����
		Iterator it = notExitslist.iterator();
		while (it.hasNext()) {
			ArrayList list2 = getNodesByParentId((String)it.next());
			for (int temp = 0; temp < list2.size(); temp++) {
				TreeNode tempNode = (TreeNode) list2.get(temp);
				tempNode.setParent(rootId);
				addParentMap(rootId, tempNode);
			}
		}
	}

	// ���ظ����뺢���ǵ�ӳ���ϵ
	private Map getParentMap() {
		return parentMap;
	}

	public static void main(String[] args) {
		TreeNode root = new TreeNode("root", "nm", "��������", null);

		TreeNode node1 = new TreeNode("1", "nm", "̫��ϵ", "root");
		TreeNode node2 = new TreeNode("2", "nm", "����", "66");
		TreeNode node3 = new TreeNode("3", "nm", "����", "1");
		TreeNode node4 = new TreeNode("4", "nm", "����", "2");
		TreeNode node5 = new TreeNode("5", "nm", "����", "2");
		TreeNode node6 = new TreeNode("6", "nm", "51������", "9");

		DwzTreeUtil tree = new DwzTreeUtil();
		tree.addNode(node1);
		tree.addNode(node2);
		tree.addNode(node3);
		tree.addNode(node4);
		tree.addNode(node5);
		tree.addNode(node6);
		System.out.println(tree.getDwzTreeString(root));
	}

	/**
	 * ����dwz���еĺ��������ַ�������.
	 * <li> <a tname="nm" tvalue="2"> ����</a>
	 * <ul>
	 * <li> <a tname="nm" tvalue="4"> ����</a>
	 * <li>
	 * <li> <a tname="nm" tvalue="5"> ����</a>
	 * <li>
	 * </ul>
	 * </li>
	 * 
	 * @param root
	 * @return
	 */
	public String getDwzTreeString(TreeNode root) {
		return getDwzTreeString(root,true);
	}
	
	/**
	 * ����dwz���еĺ��������ַ�������.
	 * @param root ���ڵ�
	 * @param countFirstLevel  �Ƿ��Զ������һ�еĽڵ�.
	 * @return
	 */
	public String getDwzTreeString(TreeNode root,boolean countFirstLevel) {
		if(countFirstLevel)
			setFistFloor(root);
		return getTree(root);
	}

	private String getTree(TreeNode root) {
		StringBuffer buffer = new StringBuffer(50);
		boolean isP = isParent(root.getId());
		if (isP) {
			buffer.append("<li><a tname=\"" + root.getName() + "\" tvalue=\""
					+ root.getId() + "\"> " + root.getDes() + "</a><ul>");
			List childList = (ArrayList) parentMap.get(root.getId());
			Iterator iterator = childList.iterator();
			while (iterator.hasNext()) {
				TreeNode node = (TreeNode) iterator.next();
				if(!node.getId().equals(root.getId()))
					buffer.append(getTree(node));
			}
			buffer.append("</ul></li>");
		} else {
			return "<li><a tname=\"" + root.getName() + "\" tvalue=\""
					+ root.getId() + "\"> " + root.getDes() + "</a><li>";
		}
		return buffer.toString();
	}

	/**
	 * ����ext��Ҫ�������ַ���.<br>
	 * [{"id":"1","name":"̫��ϵ","parent":"root","print":"0",<br>
	 * "children":[{"id":"2","name":"����","parent":"1","print":"0",<br>
	 * "children":[{"id":"4","name":"����","parent":"2","print":"0"},<br>
	 * {"id":"5","name":"����","parent":"2","print":"0"}]},<br>
	 * {"id":"3","name":"����","parent":"1","print":"0"}]},<br>
	 * {"id":"6","name":"51������","parent":"root","print":"0"}]<br>
	 * 
	 * @param tree
	 * @param root
	 * @return
	 */
	public String getExtTreeJson(TreeNode root) {
		StringBuilder ans = new StringBuilder();
		Set parentSet = getParentSet();
		Stack stack = new Stack();
		stack.push(root);
		setFistFloor(root);
		Map map = getParentMap();
		String result = "";
		ans.append("[");
		while (!stack.isEmpty()) {
			TreeNode e = (TreeNode) stack.pop();
			// �õ��ø��׵ĺ��ӽڵ���
			ArrayList childs = (ArrayList) map.get(e.getId());
			Iterator cIt = childs.iterator();
			// ����һ����ջ�Ƿ�ı�ı�־λ
			boolean stackChanged = false;
			// ������ӽڵ�ѭ��δ�������߶�ջû�иı䣬�ͽ���ѭ�����ӽڵ�Ĳ�����
			while (cIt.hasNext() && (!stackChanged)) {
				// �õ����ӽڵ�
				TreeNode aChild = (TreeNode) cIt.next();
				// ����ڵ�û�б���ӡ�����ʹ�ӡ
				if (aChild.getPrint() == 0) {
					// �������֦�ڵ� ����ֱ��ת����Ϊjson�ַ���
					if ((!parentSet.contains(aChild.getId()))) {
						ans.append(JsonUtil.bean2json(aChild) + ",");
						// ע��ڵ��ӡ֮����һ�����ţ�
						// ans.append(getExtNodeJsonStr(aChild) + ",");
						// ���ýڵ㱻��ӡ�ı�־λ��
						aChild.setPrint(1);
					}
					// ����Ƿ���֦�ڵ�ʹ�ӡһ�����ַ�����ͬʱ���ջ�����´ε�ѭ��
					else if (parentSet.contains(aChild.getId())) {
						// ʹ����һ����Դjava�������γ�json����
						ans.append(JsonUtil.bean2json(aChild));
						// ans.append(getExtNodeJsonStr(aChild));
						ans.deleteCharAt(ans.lastIndexOf("}"));
						ans.append(",\"children\":[");
						// ���øýڵ��Ѿ���ӡ
						aChild.setPrint(1);
						// ���ýڵ������ջ
						stack.push(aChild);
						// ���ö�ջ���޸��ˡ����˳����ε�whileѭ����
						stackChanged = true;
						break;
					}
				}
			}

			// �����ǽ��е�json�������鴮��
			// ��ӡ��һ�����׵�ȫ�������ǵ�json��֮��ɾ�����һ�����š��ټ���һ��"]".ͬʱ�ӵ��ø��׽ڵ㡣
			// ע���ѯ�������һ������Ϊû�д�ӡroot���json��������û�б�Ҫ�ں����������ķ�ղ�����
			if ((!cIt.hasNext()) && stackChanged == false) {
				// ע���ӡ�길��֮��Ҫ�����ַ����ķ�ղ�����
				if (e.getPrint() == 1) {
					ans.deleteCharAt(ans.lastIndexOf(","));
					ans.append("]},");
				}
				// ����ӡ��ĸ��׽ڵ�Ӷ�ջ���ӵ���
				stack.pop();
			}
		}
		// ������������ַ����ķ�ղ�����
		ans.deleteCharAt(ans.lastIndexOf(","));
		ans.append("]");
		result = ans.toString();
		return result;
	}
}
