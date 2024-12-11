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
* Created Date: 2024/12/7  13:13 */
package cn.winfxk.lexy.z1.ui.cp.call.to

import cn.winfxk.lexy.z1.Deploy.Companion.deploy
import cn.winfxk.lexy.z1.client.NettyClient
import cn.winfxk.lexy.z1.client.message.Message
import cn.winfxk.lexy.z1.message.MessageType
import cn.winfxk.lexy.z1.ui.GUI
import cn.winfxk.lexy.z1.ui.cp.call.Section
import cn.winfxk.libk.log.Log
import cn.winfxk.libk.tool.Tool
import cn.winfxk.tool.view.MyJFrame
import cn.winfxk.tool.view.MyJPanel
import com.alibaba.fastjson2.JSONObject
import com.sun.awt.AWTUtilities
import java.awt.Color
import java.awt.event.WindowEvent
import java.awt.event.WindowListener
import java.awt.geom.RoundRectangle2D
import java.util.*

class Callto(private val string: String, private val select: Section) : MyJPanel(), AutoCloseable, WindowListener, Runnable {
    private val log = Log(this.javaClass.simpleName)
    private val frame = MyJFrame();
    private val closeview: Closeview
    private val contentPane: ContentPane;
    private val tip = "正在${select.getString()}"
    @Volatile
    private var isRunning = true;

    init {
        log.i("正在构建传呼页面")
        closeview = Closeview(this);
        contentPane = ContentPane(this);
        frame.iconImage = deploy.getCallIcon();
        frame.defaultCloseOperation = MyJFrame.EXIT_ON_CLOSE
        frame.size = GUI.getMain().frame.size;
        frame.isUndecorated = true
        frame.title = tip
        frame.isAlwaysOnTop = true;
        frame.contentPane = this;
        frame.addComponentListener(this);
        frame.addWindowListener(this)
        frame.background = Color.white;
        frame.setLocation(deploy.screenSize.width / 2 - frame.width / 2, deploy.screenSize.height / 2 - frame.height / 2);
        AWTUtilities.setWindowShape(frame, RoundRectangle2D.Double(0.0, 0.0, frame.width.toDouble(), frame.height.toDouble(), 10.0, 10.0))
        size = frame.size
        closeview.setLocation(0, 0);
        add(closeview);
        add(contentPane);
        Thread(this).start();
        Thread(this::sendMessage).start();
    }

    override fun start() {
        closeview.setSize(width, 24);
        closeview.start();
        contentPane.setSize(width, height - closeview.height);
        contentPane.setLocation(0, closeview.height);
        contentPane.start();
    }

    fun showFrame() {
        if (! frame.isVisible) frame.isVisible = true;
        frame.toFront()
        frame.requestFocus();
        log.i("显示传呼界面${select.javaClass.simpleName}-$string")
    }

    override fun close() {
        isRunning = false;
        frame.dispose();
        log.i("关闭传呼界面${select.javaClass.simpleName}-$string")
    }

    override fun windowOpened(e: WindowEvent?) {
    }

    override fun windowClosing(e: WindowEvent?) {
        close();
    }

    override fun windowClosed(e: WindowEvent?) {
    }

    override fun windowIconified(e: WindowEvent?) {
    }

    override fun windowDeiconified(e: WindowEvent?) {
    }

    override fun windowActivated(e: WindowEvent?) {
    }

    override fun windowDeactivated(e: WindowEvent?) {
    }

    override fun run() {
        log.i("呼叫流光颜色启动")
        val color = GetColor(frame.background);
        while (isRunning) {
            color.start();
            frame.background = color.getColor();
            contentPane.setFontColor(color.getForeground());
            Tool.sleep(100);
        }
        log.i("呼叫流光服务终止！")
    }

    private fun sendMessage() {
        log.i("启动循环呼叫服务！")
        Tool.sleep(1000);
        val netty = NettyClient.getMain();
        val json = JSONObject();
        json["to"] = select.getString()
        json["at"] = select.getType();
        json["type"] = "Call";
        json["string"] = string
        json["title"] = deploy.Title;
        json["date"] = System.currentTimeMillis();
        json["CallID"] = UUID.randomUUID().toString();
        log.i("呼叫信息: $json")
        var isCall = false;
        val message = "${deploy.Title} ${string}${select.getString()}";
        while (isRunning) {
            if (netty.isConnected()) {
                try {
                    netty.sendMessage(Message(
                        isSuccess = true,
                        type = MessageType.Request,
                        message = message,
                        json = json,
                    ), isPrintlog = ! isCall) {
                        if (! isCall) {
                            log.i(message)
                            isCall = true;
                        }
                    }
                    contentPane.setString("已发起呼叫[${select.getString()}]..")
                } catch (e: Exception) {
                    log.e("像服务器发起呼叫请求时出现异常！", e)
                    contentPane.setString("呼叫异常！！${e.message}");
                }
            } else contentPane.setString("无法连接至服务器！");
            Tool.sleep(1000)
        }
        log.i("循环呼叫服务终止！")
    }
}