package com.bg.util;

import com.bg.entity.SystemMenu;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 将一条一条菜单数据整合成多级菜单
 * @author ctp
 * @date 2022/11/23 23:11
 */
public class SystemMenusUtil {

    /**
     * 转换成有关系的多级菜单结构
     * @param menus 菜单的list数据
     * @return List<SystemMenu>
     */
    // TODO 优化算法
    public static List<SystemMenu> multiMenus(List<SystemMenu> menus){
        Collections.sort(menus);
        List<SystemMenu> menuList = new ArrayList<>();// 已经处理好的list
        for (int i = 0; i < menus.size(); i++) {
            if (menus.get(i).getParentId() == null){
                menuList.add(menus.get(i)); // 添加根节点到结果列表
//                menus.remove(menus.get(i)); // 从旧节点集合删除根节点，然后将集合交给下次循环
//                System.out.println("列表长度：" + menus.size());
                menus.get(i).setChildren(findChildrenNote(menus.get(i), menus));// 查找节点的所有子节点 赋值给当前根节点
//                i--;// 元素往被删除的元素的位置靠拢
            }
            //else{
////                System.out.println("未找到根节点，不删除元素");
//            }
        }
//        System.out.println("搜索完毕...");
        return menuList;
    }

    public static List<SystemMenu> findChildrenNote(SystemMenu root, List<SystemMenu> nodes){
        System.out.println("列表长度：" + nodes.size());
        ArrayList<SystemMenu> menus1 = new ArrayList<>();//  返回给父节点做子节点集合
        for (int i = 0; i < nodes.size(); i++) {
            if (root.getId().equals(nodes.get(i).getParentId())){// 找到根节点的子节点
                menus1.add(nodes.get(i)); // 添加到子节点集合
//                nodes.remove(nodes.get(i));// 删除元素
                nodes.get(i).setChildren(findChildrenNote(nodes.get(i), nodes)); // 为当前节点寻找子节点
//                i--;// 元素往被删除的元素的位置靠拢
            }
        }
        nodes.removeAll(menus1); // 删除就位的节点
        return menus1;
    }
}
