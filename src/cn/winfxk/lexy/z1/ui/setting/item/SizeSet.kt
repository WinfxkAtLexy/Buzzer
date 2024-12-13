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
* Created Date: 2024/12/12  15:29 */
package cn.winfxk.lexy.z1.ui.setting.item

import cn.winfxk.lexy.z1.Deploy
import cn.winfxk.lexy.z1.client.message.call.cp.ContentPane
import cn.winfxk.libk.config.Config
import cn.winfxk.libk.tool.utils.objToInt
import cn.winfxk.tool.view.dialog.Toast
import javax.swing.JLabel
import javax.swing.JTextField
import javax.swing.SwingConstants

class SizeSet : BaseItem() {
    private val label = JLabel("报警字体大小：");
    private val edit = JTextField("${Deploy.deploy.config.getInt("报警页面字体大小", 100)}");

    init {
        label.setLocation(0, 0);
        label.verticalAlignment = SwingConstants.CENTER;
        label.font = labelFont;
        label.foreground = labelColor;
        add(label);
        edit.font = lvalueFont;
        edit.foreground = lvalueColor;
        edit.toolTipText = "请输入报警页面字体大小"
        add(edit);
    }

    override fun start() {
        label.setSize(labelFont.size * (label.text.length + 1), height);
        val ih = height - jg * 2;
        edit.setLocation(label.x + label.width + jg, height / 2 - ih / 2)
        edit.setSize(width - edit.x, ih);
    }

    override fun save(config: Config) {
        val text = edit.text.objToInt();
        config.set("报警页面字体大小", text);
        ContentPane.getMain().setFontsize(text);
    }

    override fun legal(): Boolean {
        val text = edit.text;
        if (text.isNullOrBlank()) {
            Toast("报警字体大小不能为空！")
            return false
        }
        val size = text.objToInt(- 1);
        if (size <= 0) {
            Toast("报警字体大小必须是正整数！")
            return false;
        }
        return true;
    }
}