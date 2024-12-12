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
* Created Date: 2024/12/11  16:19 */
package cn.winfxk.lexy.z1.ui.setting.item

import cn.winfxk.lexy.z1.ui.setting.ContentPane
import cn.winfxk.libk.config.Config
import cn.winfxk.libk.tool.utils.objToInt
import cn.winfxk.tool.view.dialog.Toast
import javax.swing.JLabel
import javax.swing.JTextField
import javax.swing.SwingConstants

class ServerSet(private val main: ContentPane) : BaseItem() {
    private val label = JLabel("服务器地址：");
    private val edit = JTextField("${deploy.host}:${deploy.port}");

    init {
        label.setLocation(0, 0);
        label.verticalAlignment = SwingConstants.CENTER;
        label.font = labelFont;
        label.foreground = labelColor;
        add(label);
        edit.font = lvalueFont;
        edit.foreground = lvalueColor;
        edit.toolTipText = "请输入服务器地址"
        add(edit);
    }

    override fun start() {
        label.setSize(labelFont.size * (label.text.length + 1), height);
        val ih = height - jg * 2;
        edit.setLocation(label.x + label.width + jg, height / 2 - ih / 2)
        edit.setSize(width - edit.x, ih);
    }

    override fun save(config: Config) {
        val host = Host(edit.text);
        deploy.host = host.host !!;
        deploy.port = (host.port !!).toInt();
        config.set("host", deploy.host);
        config.set("port", deploy.port);
        main.log.i("客户端设置服务器地址为：${deploy.host}:${deploy.port}")
    }

    override fun legal(): Boolean {
        val string = edit.text;
        if (string.isNullOrBlank()) {
            Toast("服务器地址不能为空！")
            return false
        }
        val host = Host(string);
        if (host.host.isNullOrBlank()) {
            Toast("服务器地址不能为空！")
            return false
        }
        if (host.port.isNullOrBlank()) {
            Toast("服务器端口不能为空！")
            return false
        }
        val port = host.port.objToInt(- 1);
        if (port <= 0) {
            Toast("服务器端口仅支持一个大于零的整数！")
            return false
        }
        if (! deploy.isValidAddress(host.host)) {
            Toast("输入的服务器地址不合法！")
            return false
        }
        return true;
    }

    private class Host(string: String) {
        var host: String? = null;
        var port: String? = null;

        init {
            reset(string);
        }

        private fun reset(string: String) {
            val ss = string.split(":");
            if (ss.isNotEmpty()) host = ss[0];
            if (ss.size > 1) port = ss[1];
        }
    }
}