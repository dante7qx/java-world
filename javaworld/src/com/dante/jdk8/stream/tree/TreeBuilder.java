package com.dante.jdk8.stream.tree;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import cn.hutool.core.bean.BeanUtil;

/**
 * 树构造器
 * 
 * @param <T>
 */
public class TreeBuilder<T> {
	
	 // 默认ID域名称
    private final String DEFAULT_ID_FIELD = "id";
    // 默认父ID域名称
    private final String DEFAULT_PID_FIELD = "parentId";
    // 默认Children域名称
    private final String DEFAULT_CHILDREN_FIELD = "children";

    // list
    private List<T> list = new ArrayList<>();
    // tree
    private List<T> tree = new ArrayList<>();
    
    // 获取实体id属性的方法, 默认getId()
    private Function<? super T, Object> getIdFunc = e -> BeanUtil.getProperty(e, DEFAULT_ID_FIELD);

    // 获取实体pid属性的方法, 默认getParentId()
    private Function<? super T, Object> getPidFunc = e -> BeanUtil.getProperty(e, DEFAULT_PID_FIELD);

    // 获取实体children属性的方法, 默认getChildren
    private Function<? super T, List<T>> getChildrenFunc = e -> BeanUtil.getProperty(e, DEFAULT_CHILDREN_FIELD);

    // set实体children属性的方法, 默认setChildren(List<T> list)
    private ChildrenConsumer<T> setChildrenFunc = (e, l) -> BeanUtil.setFieldValue(e, DEFAULT_CHILDREN_FIELD, l);

    // 比较器，用于建树时排序
    private Comparator<T> comparator;
    
    /**
     * 内部函数式接口：Children消费类
     */
    @FunctionalInterface
    public interface ChildrenConsumer<T> {
        void accept(T t, List<T> list);
    }

	/***
	 * 指定列表
	 *
	 * @param list 数据列表
	 * @return com.ccxi.hub.utils.TreeBuilder<T>
	 */
	public TreeBuilder<T> list(List<T> list) {
		this.list = list;
		return this;
	}

	/***
	 * 指定树
	 *
	 * @param tree 数据树
	 * @return com.ccxi.hub.utils.TreeBuilder<T>
	 */
    public TreeBuilder<T> tree(List<T> tree) {
        this.tree = tree;
        return this;
    }

    /***
     * 指定ID函数
     *
     * @param getIdFunc 获取id的方法
     * @return com.ccxi.hub.utils.TreeBuilder<T>
     */
    public TreeBuilder<T> asId(Function<? super T, Object> getIdFunc) {
        this.getIdFunc = getIdFunc;
        return this;
    }

    /***
     * 指定PID函数
     *
     * @param getPidFunc 获取Pid的方法
     * @return com.ccxi.hub.utils.TreeBuilder<T>
     */
    public TreeBuilder<T> asPid(Function<? super T, Object> getPidFunc) {
        this.getPidFunc = getPidFunc;
        return this;
    }

    /***
     * 指定children函数
     *
     * @param setChildrenFunc setChildren方法
     * @return com.ccxi.hub.utils.TreeBuilder<T>
     */

    public TreeBuilder<T> getChildren(Function<? super T, List<T>> getChildrenFunc) {
        this.getChildrenFunc = getChildrenFunc;
        return this;
    }


    public TreeBuilder<T> setChildren(ChildrenConsumer<T> setChildrenFunc) {
        this.setChildrenFunc = setChildrenFunc;
        return this;
    }

    /***
     * 指定排序
     *
     * @param Comparator 排序器
     * @return com.ccxi.hub.utils.TreeBuilder<T>
     */
    public TreeBuilder<T> sorted(Comparator<T> comparator) {
        this.comparator = comparator;
        return this;
    }

    /***
     * 建树
     *
     * @return com.ccxi.hub.utils.TreeBuilder<T>
     */
    public List<T> build() {
        if (null != list && list.size() > 0) new ArrayList<>();
        Map<String, List<T>> childrenMap = new HashMap<>();
        Map<String, T> idMap = new HashMap<>();
        list.forEach(e -> {
            // idMap设置
            String id = Optional.ofNullable(getIdFunc.apply(e)).map(Object::toString).orElse(null);
            idMap.put(id, e);
            // childrenMap设置
            String pid = Optional.ofNullable(getPidFunc.apply(e)).map(Object::toString).orElse(null);
            if (!childrenMap.containsKey(pid)) {
                childrenMap.put(pid, new ArrayList<>());
            }
            childrenMap.get(pid).add(e);
        });
        // 建树：parent为null的节点作为根节点
        Stream<T> stream = list.stream().filter(e -> null == idMap.get(Optional.ofNullable(getPidFunc.apply(e)).map(Object::toString).orElse(null)));
        if (null != comparator) {
            stream = stream.sorted(comparator);
        }
        tree = stream.collect(Collectors.toList());
        // 自顶向下递归查找children
        findChildren(tree, childrenMap);
        return tree;
    }

    /***
     * 展开
     *
     * @return com.ccxi.hub.utils.TreeBuilder<T>
     */
    public List<T> expand() {
        if (null == tree || tree.size() == 0) return new ArrayList<>();
        list = new ArrayList<>();
        expandChildren(list, tree);
        return list;
    }

    /***
     * 递归构建chidlren
     */
    private void findChildren(List<T> list, Map<String, List<T>> map) {
        if (null == list || list.size() == 0) return;
        list.forEach(e -> {
            String id = Optional.ofNullable(getIdFunc.apply(e)).map(Object::toString).orElse(null);
            List<T> children = map.containsKey(id) ? map.get(id) : new ArrayList<>();
            if (null != comparator) {
                children = children.stream().sorted(comparator).collect(Collectors.toList());
            }
            setChildrenFunc.accept(e, children);
            findChildren(children, map);
        });
    }

    /***
     * 递归展开chidlren
     */
    private void expandChildren(List<T> res, List<T> list) {
        if (null == list || list.size() == 0) return;
        res.addAll(list);
        list.forEach(e -> {
            expandChildren(res, getChildrenFunc.apply(e));
            setChildrenFunc.accept(e, new ArrayList<>());
        });
    }

}
