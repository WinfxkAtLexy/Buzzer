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
* Created Date: 2024/12/10  17:16 */
package cn.winfxk.lexy.z1.client.message.call.cp

import cn.winfxk.lexy.z1.Deploy
import cn.winfxk.lexy.z1.client.message.call.CallFrame
import cn.winfxk.lexy.z1.client.message.call.CalltoService
import cn.winfxk.lexy.z1.isRunning
import cn.winfxk.lexy.z1.ui.GUI
import cn.winfxk.lexy.z1.ui.cp.call.to.GetColor
import cn.winfxk.libk.tool.Tool
import cn.winfxk.tool.view.MyJPanel
import cn.winfxk.tool.view.toCenter
import java.awt.Color
import java.awt.Font
import javax.swing.JLabel

class ContentPane(private val main: CallFrame) : MyJPanel(), Runnable {
    private val label = JLabel();
    private val getColor: GetColor;

    companion object {
        private lateinit var main: ContentPane;
        fun getMain() = main;
    }

    init {
        ContentPane.main = this;
        label.isOpaque = true;
        label.background = GUI.backg;
        label.setLocation(0, 0);
        label.toCenter()
        label.font = Font("楷体", Font.BOLD, Deploy.deploy.config.getInt("报警页面字体大小", 100));
        getColor = GetColor(label.background);
        Thread(this::onColor).start();
        add(label);
    }

    fun setFontsize(size: Int) {
        label.font = Font("楷体", Font.BOLD, size);
    }

    fun getFontsize() = label.font.size;
    override fun start() {
        label.size = size;
    }

    override fun run() {
        val sleep = 2500L;
        while (isRunning) {
            Tool.sleep(sleep)
            if (! main.isRunning()) continue;
            for (message in ArrayList(CalltoService.getMain().getList().values)) {
                if (message == null) continue;
                label.text = message.json.let {
                    val callJson = it.getJSONObject("json");
                    val ph = (callJson["string"] ?: "").toString();
                    "<html><center>${it["Client"]} - ${ph}</center><br><center>${it["to"]}</center></html>"
                }
                Tool.sleep(sleep)
            }
        }
    }

    private fun onColor() {
        val colors = arrayOf(
            Color.black, Color.blue,
            Color.red, Color.green)
        while (isRunning) {
            Tool.sleep(1000);
            if (! main.isRunning()) continue;
            label.foreground = colors[Tool.getRand(0, colors.size - 1)];
        }
    }
}