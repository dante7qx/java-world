package com.dante.jdk8.stream.tree;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.dante.jdk8.stream.vo.Menu;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.json.JSONUtil;

@DisplayName("Java8 Stream 操作树形结构")
public class MenuTree {
	private static List<Menu> menus = null;
	
	@BeforeAll
	public static void init() {
		menus = Arrays.asList(
			new Menu(1, "根节点", 0, "根节点"), 
			new Menu(2, "子节点1", 1, "子节点1"), 
			new Menu(3, "子节点1.1", 2, "子节点1.1"),
			new Menu(4, "子节点1.2", 2, "子节点1.2"), 
			new Menu(5, "根节点1.3", 2, "根节点1.3"), 
			new Menu(6, "根节点2", 1, "根节点2"), 
			new Menu(7, "根节点2.1", 6, "根节点2.1"),
			new Menu(8, "根节点2.2", 6, "根节点2.2"), 
			new Menu(9, "根节点2.2.1", 7, "根节点2.2.1"), 
			new Menu(10, "根节点2.2.2", 7, "根节点2.2.2"),
			new Menu(11, "根节点3", 1, "根节点3"), 
			new Menu(12, "根节点3.1", 11, "根节点3.1"),
			
			new Menu(100, "根节点100", 0, "根节点100"),
			new Menu(101, "根节点100-1", 100, "根节点100-1")
			
		);
	}
	
	@Test
	@Disabled
	public void testTree() {
		// 获取父节点
		List<Menu> collect = menus.stream()
				.filter(m -> m.getParentId() == 0)
				.map(m -> {
					m.setChildren(getChildrens(m, menus));
					return m;
				})
				.collect(Collectors.toList());
		System.out.println("-------转json输出结果-------");
		System.out.println(JSONUtil.toJsonPrettyStr(collect));
	}
	
	/**
	 * 递归查询子节点
	 * 
	 * @param root 根节点
	 * @param all  所有节点
	 * @return 根节点信息
	 */
	private List<Menu> getChildrens(Menu root, List<Menu> all) {
		List<Menu> children = all.stream().filter(m -> {
			return Objects.equals(m.getParentId(), root.getId());
		}).map((m) -> {
			m.setChildren(getChildrens(m, all));
			return m;
		}).collect(Collectors.toList());
		return children;
	}
	
	@Test
	public void testTree2() {
		List<Menu> trees = TreeUtils.list2Tree(menus);
		System.out.println(JSONUtil.toJsonPrettyStr(trees));
		// TreeBuilder builder = new TreeBuilder<Org>().asId(Org::getCode).asPid(Org::getPcode).setChildren(Org::setChildOrgs);
	}
	
	@Test
	public void testTree3() {
		// 1. 查数据
		// 2. 配置
		TreeNodeConfig config = new TreeNodeConfig();
//		config.setIdKey("id");						// 默认为id可以不设置
//      config.setParentIdKey("parentId");  		// 默认为parentId可以不设置
//		config.setChildrenKey("children");  		// 子点 默认为children
//      config.setDeep(4);							// 最大递归深度
//      config.setWeightKey("priority");			// 排序字段
		
		// 3. 转树，Tree<>里面泛型为id的类型
		List<Tree<Integer>> trees = TreeUtil.build(menus, 0, config, (node, tree) -> {
            tree.setId(node.getId());
            tree.setName(node.getName());
            tree.setParentId(node.getParentId());
            // 额外的值
            tree.put("label", node.getName());
            tree.put("data", node);
        });
//		System.out.println(JSONUtil.toJsonPrettyStr(trees));
		
		Tree<Integer> tree = trees.get(0).getChildren().get(1);
		System.out.println(tree);
		System.out.println(TreeUtil.getParentsName(tree, false));
		System.out.println(TreeUtil.getParentsName(tree, true));
	}
	
}
