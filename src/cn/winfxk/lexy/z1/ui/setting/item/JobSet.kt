/* 
* Copyright Notice
* © [2024] Winfxk. All rights reserved.
* The software, its source code, and all related documentation are the intellectual property of Winfxk. Any reproduction or distribution of this software or any part thereof must be clearly attributed to Winfxk and the original author. Unauthorized copying, reproduction, or distribution without proper attribution is strictly prohibited.
* For inquiries, support, or to request permission for use, please contact us at:
* Email: admin@winfxk.cn
* QQ: 2508543202
* Visit our homepage for more information: http://Winfxk.cn
* 
* --------- Create message ---------
* Created by IntelliJ ID
* Author： Winfxk
* Created PCUser: kc4064 
* Web: http://winfxk.com
* Created Date: 2024/12/11  15:38 */
package cn.winfxk.lexy.z1.ui.setting.item

import cn.winfxk.lexy.z1.ui.GUI
import cn.winfxk.lexy.z1.ui.cp.call.ps.ContentPane
import cn.winfxk.libk.config.Config
import cn.winfxk.tool.view.dialog.Toast
import java.awt.Font
import javax.swing.*

class JobSet : BaseItem() {
    private val label = JLabel("作业站号：");
    private val edit = JTextField(getString());
    private val jsp = JScrollPane(edit, ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    private val lvalueFont = Font("楷体", Font.PLAIN, 15);

    init {
        label.setLocation(0, 0);
        label.verticalAlignment = SwingConstants.CENTER;
        label.font = labelFont;
        label.foreground = labelColor;
        add(label);
        edit.font = lvalueFont;
        edit.foreground = lvalueColor;
        edit.setLocation(0, 0)
        edit.toolTipText = "请输入可选工作站"
        jsp.background = GUI.backg;
        jsp.isOpaque = false;
        add(jsp);
    }

    override fun start() {
        label.setSize(labelFont.size * (label.text.length + 1), height);
        val ih = height - jg * 2;
        jsp.setLocation(label.x + label.width + jg, height / 2 - ih / 2)
        jsp.setSize(width - jsp.x, ih);
        edit.preferredSize = jsp.size;
    }

    override fun save(config: Config) {
        val list = getList(edit.text)
        ContentPane.setList(list)
        config.set("CallButtons", list);
    }

    override fun legal(): Boolean {
        val text = edit.text;
        if (text.isNullOrBlank()) {
            Toast("岗位信息不能为空！！")
            return false
        }
        val list = getList(text);
        if (list.isEmpty()) {
            Toast("请至少添加一个岗位！")
            return false;
        }
        return true;
    }

    private fun getString(): String {
        val list = ContentPane.getList();
        var string = "";
        for (item in list)
            string += (if (string.isBlank()) "" else ",") + item;
        return string;
    }

    private fun getList(string: String): ArrayList<String> {
        val list = ArrayList<String>();
        val strings = string.split(",");
        for (s in strings) if (s.isNotBlank()) list.add(s)
        return list;
    }
}