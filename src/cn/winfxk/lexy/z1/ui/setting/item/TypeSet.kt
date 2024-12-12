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
* Created Date: 2024/12/12  08:05 */
package cn.winfxk.lexy.z1.ui.setting.item

import cn.winfxk.lexy.z1.ui.GUI
import cn.winfxk.lexy.z1.ui.cp.call.IE
import cn.winfxk.lexy.z1.ui.cp.call.IQC
import cn.winfxk.lexy.z1.ui.cp.call.Technology
import cn.winfxk.lexy.z1.ui.cp.call.Warehouse
import cn.winfxk.lexy.z1.ui.setting.ContentPane
import cn.winfxk.libk.config.Config
import cn.winfxk.tool.view.dialog.Toast
import javax.swing.JComboBox
import javax.swing.JLabel
import javax.swing.SwingConstants

class TypeSet(private val main: ContentPane) : BaseItem() {
    private val label = JLabel("客户端类型：");
    private val edit = JComboBox(types);
    private val maxItemWidth: Int;

    companion object {
        val types = arrayOf(
            "All",
            IE::class.java.simpleName,
            IQC::class.java.simpleName,
            Technology::class.java.simpleName,
            Warehouse::class.java.simpleName
        );
    }

    init {
        var iw = 0;
        for (type in types) if (iw < type.length) iw = type.length;
        maxItemWidth = (iw + 1) * lvalueFont.size;
        label.setLocation(0, 0);
        label.verticalAlignment = SwingConstants.CENTER;
        label.font = labelFont;
        label.foreground = labelColor;
        add(label);
        edit.font = lvalueFont;
        edit.foreground = lvalueColor;
        edit.background = GUI.backg;
        edit.toolTipText = "请选择客户端类型"
        edit.selectedItem = deploy.ClientType;
        add(edit);
    }

    override fun start() {
        label.setSize(labelFont.size * (label.text.length + 1), height);
        val ih = height - jg * 2;
        edit.setLocation(label.x + label.width + jg, height / 2 - ih / 2)
        edit.setSize((width - edit.x).coerceAtMost(maxItemWidth), ih);
    }

    override fun save(config: Config) {
        val text = edit.selectedItem !!.toString();
        deploy.ClientType = text;
        main.log.i("客户端设置客户端类型为：${deploy.ClientType}")
        config.set("ClientType", deploy.ClientType)
    }

    override fun legal(): Boolean {
        val text = edit.selectedItem?.toString();
        if (text.isNullOrBlank()) {
            Toast("请选择客户端类型");
            return false;
        }
        return true;
    }
}