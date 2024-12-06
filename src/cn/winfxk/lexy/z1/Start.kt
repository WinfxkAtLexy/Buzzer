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
* Created Date: 2024/11/20  16:57 */
package cn.winfxk.lexy.z1

import cn.winfxk.lexy.z1.client.NettyClient
import cn.winfxk.lexy.z1.tray.MySystemTray
import cn.winfxk.lexy.z1.ui.GUI
import cn.winfxk.lexy.z1.ui.mini.Main
import cn.winfxk.libk.log.Log
import cn.winfxk.libk.tool.Tool
import cn.winfxk.libk.tool.tab.Closeable
import cn.winfxk.tool.view.JOptionPane
import cn.winfxk.tool.view.MyJFrame
import cn.winfxk.tool.view.MyJPanel
import cn.winfxk.tool.view.dialog.Toast
import com.sun.awt.AWTUtilities
import java.awt.Color
import java.awt.Font
import java.awt.Window
import java.awt.geom.RoundRectangle2D
import javax.swing.JLabel
import javax.swing.SwingConstants
import kotlin.system.exitProcess

class Start(isTop: Boolean = true) : MyJPanel() {
    val frame = MyJFrame();
    private val label = JLabel("初始化中请稍后...");
    @Volatile
    private var isRunning = true;

    companion object {
        private val deploy = Deploy.deploy;
        private val width = Tool.getMath(600, 400, (deploy.screenSize.width / 4));
        private val height = Tool.getMath(300, 150, (deploy.screenSize.height / 3.6).toInt());
        private val x = deploy.screenSize.width / 2 - width / 2;
        private val y = deploy.screenSize.height / 2 - height / 2;
        private val backg = Color.WHITE;
        private val font = Font("楷体", Font.BOLD, 17);
    }

    init {
        frame.type = Window.Type.UTILITY;
        frame.setBounds(Start.x, Start.y, Start.width, Start.height);
        frame.background = backg;
        frame.isAlwaysOnTop = isTop;
        frame.contentPane = this;
        frame.isUndecorated = true;
        frame.type = Window.Type.UTILITY
        val w = frame.width.toDouble();
        val h = frame.height.toDouble();
        AWTUtilities.setWindowShape(frame, RoundRectangle2D.Double(0.0, 0.0, w, h, w / 10, h / 10))
        label.font = Start.font;
        label.background = backg;
        label.isOpaque = false;
        label.setLocation(0, 0)
        label.horizontalAlignment = SwingConstants.CENTER;
        label.verticalAlignment = SwingConstants.CENTER;
        add(label);
    }

    override fun start() {
        label.size = size;
    }

    fun showFrame(title: String? = null, alpha: Boolean = true) {
        frame.isVisible = true;
        if (title != null) label.text = title;
        start();
        if (alpha) Thread {
            frame.opacity = 0.0f;
            for (i in 0 .. 100) {
                if (! isRunning) break;
                frame.opacity = i.toFloat() / 100;
                Tool.sleep(1)
            }
            if (isRunning) frame.opacity = 1f;
        }.start()
    }
    @Volatile
    private var setAlpha = 0L;
    fun toOpacity(alpha: Float) {
        val time = System.currentTimeMillis();
        setAlpha = time;
        var run = true;
        Thread {
            val function = if (alpha >= frame.opacity) fun() {
                val op = frame.opacity + 0.01f;
                if (op >= 1 || op >= alpha) run = false;
                frame.opacity = op.coerceAtMost(1f)
            } else fun() {
                val op = frame.opacity - 0.01f;
                if (op <= 0 || op <= alpha) run = false;
                frame.opacity = 0f.coerceAtLeast(op);
            }
            while (isRunning && time == setAlpha && run) {
                function();
                Tool.sleep(3)
            }
        }.start();
    }

    override fun isVisible(): Boolean {
        return frame.isVisible
    }

    override fun setTitle(title: String) {
        label.text = title;
    }

    fun hideFrame(alpha: Boolean = true, closed: Boolean = false) {
        isRunning = false;
        if (alpha) Thread {
            frame.opacity = 1.0f;
            for (i in 100 downTo 0) {
                frame.opacity = i.toFloat() / 100;
                Tool.sleep(1)
            }
            frame.isVisible = false;
            if (closed) frame.dispose()
        }.start() else {
            frame.isVisible = false;
            if (closed) frame.dispose()
        }
    }
}

var isRunning = false;
fun main() {
    Log.i("程序初始化...")
    val log = Log("init");
    try {
        isRunning = true;
        val start = Start(true);
        start.showFrame();
        Tool.sleep(1000)
        val deploy = Deploy.deploy;
        if (deploy.host.isBlank()) {
            start.toOpacity(0.7f);
            log.w("服务器地址未设置！正在弹出窗口")
            var value = JOptionPane.showInputDialog(start, "请设置服务器地址", "提示", JOptionPane.PLAIN_MESSAGE);
            if (value == null) {
                log.e("用户取消了服务器地址设置！程序即将退出")
                JOptionPane.showMessageDialog(start, "必须设置服务器地址才能使用！\n请设置服务器地址")
                close(- 1)
                return;
            }
            while (value.isNullOrBlank() || ! deploy.isValidAddress(value)) {
                log.w("用户在输入服务器地址时输入了非法数据，正在尝试让其从新输入")
                Toast("输入的服务器地址不合法！请重新输入！");
                value = JOptionPane.showInputDialog(start, "请设置服务器地址", "提示", JOptionPane.PLAIN_MESSAGE)
            }
            deploy.host = value;
            log.i("设置服务器地址为$value")
            var port = deploy.config.getString("port");
            if (port.isNullOrBlank()) {
                port = JOptionPane.showInputDialog(start, "请设置服务器端口\n默认1998", "提示", JOptionPane.PLAIN_MESSAGE)
                while (port.isNullOrBlank() || ! Tool.isInteger(port) || port.toInt() <= 0) {
                    log.w("用户在输入服务器端口时输入了非法数据，正在尝试让其从新输入")
                    Toast("输入的服务器端口不合法！请重新输入！");
                    port = JOptionPane.showInputDialog(start, "请设置服务器端口", "提示", JOptionPane.PLAIN_MESSAGE)
                }
                deploy.port = port.toInt();
                log.i("设置服务器端口为${deploy.port}")
                deploy.config.set("port", deploy.port);
            }
            deploy.config.set("host", deploy.host).save();
            start.toOpacity(1.0f);
        }
        deploy.init();
        NettyClient(start).start()
        GUI().also { if (deploy.config.getBoolean("显示用户界面", true)) it.showFrame() else log.i("未开启主界面显示，跳过界面显示") }
        Main().also { if (deploy.config.getBoolean("显示报警图标", true)) it.showWindow() else log.i("未开启报警显示，跳过图标显示") };
        MySystemTray().start();
        start.hideFrame(closed = true);
        log.i("客户端已启动并连接到服务器")
    } catch (e: Exception) {
        log.e("客户端启动异常！", e)
        JOptionPane.showMessageDialog(null, "客户端启动出现异常！请稍后重试！！\n${e.message}")
        close(- 1);
    }
}

fun close(stste: Int = 0) {
    isRunning = false;
    Log.i("正在结束进程和服务($stste).")
    Closeable.close(Main.getMain(), NettyClient.getMain(), GUI.getMain(), MySystemTray.getMain())
    Log.i("关闭应用程序...")
    exitProcess(stste)
}