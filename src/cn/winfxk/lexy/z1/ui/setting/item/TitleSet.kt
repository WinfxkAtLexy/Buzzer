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
* Created Date: 2024/12/11  16:11 */
package cn.winfxk.lexy.z1.ui.setting.item

import cn.winfxk.lexy.z1.ui.GUI
import cn.winfxk.lexy.z1.ui.cp.tv.TitleView
import cn.winfxk.libk.config.Config
import cn.winfxk.tool.view.dialog.Toast
import javax.swing.JLabel
import javax.swing.JTextField
import javax.swing.SwingConstants

class TitleSet : BaseItem() {
    private val label = JLabel("客户端标题：");
    private val edit = JTextField(deploy.Title);

    init {
        label.setLocation(0, 0);
        label.verticalAlignment = SwingConstants.CENTER;
        label.font = labelFont;
        label.foreground = labelColor;
        add(label);
        edit.font = lvalueFont;
        edit.foreground = lvalueColor;
        edit.toolTipText = "请输入客户端标题！这个标题只会在您的电脑上可见（应用程序标题名）"
        add(edit);
    }

    override fun start() {
        label.setSize(labelFont.size * (label.text.length + 1), height);
        val ih = height - jg * 2;
        edit.setLocation(label.x + label.width + jg, height / 2 - ih / 2)
        edit.setSize(width - edit.x, ih);
    }

    override fun save(config: Config) {
        deploy.Title = edit.text;
        GUI.getMain().frame.title = deploy.Title;
        TitleView.setTitle(deploy.Title)
        config.set("Title", deploy.Title);
    }

    override fun legal(): Boolean {
        if (edit.text.isNullOrBlank()) {
            Toast("客户端标题不能为空！")
            return false
        }
        return true;
    }
}