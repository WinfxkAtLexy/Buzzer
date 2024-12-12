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
* Created Date: 2024/12/10  11:23 */
package cn.winfxk.lexy.z1.client.message.call

import cn.winfxk.lexy.z1.Deploy.Companion.deploy
import cn.winfxk.lexy.z1.client.message.call.cp.ContentPane
import cn.winfxk.lexy.z1.ui.GUI.Companion.backg
import cn.winfxk.libk.log.Log
import cn.winfxk.tool.view.MyJFrame
import cn.winfxk.tool.view.MyJPanel
import java.awt.event.WindowEvent
import java.awt.event.WindowListener

class CallFrame private constructor() : MyJPanel(), AutoCloseable, WindowListener {
    private val log = Log(this.javaClass.simpleName)
    private val frame: MyJFrame;
    @Volatile private var isRuning = true;
    private val contentPane: ContentPane;

    companion object {
        private val main = CallFrame();
        fun getMain() = main
    }

    init {
        log.i("正在初始化报警界面.")
        frame = MyJFrame();
        frame.iconImage = deploy.getIcon();
        frame.background = backg;
        frame.size = deploy.screenSize;
        frame.setLocation(0, 0);
        frame.isAlwaysOnTop = true;
        frame.contentPane = this;
        frame.isUndecorated = true;
        frame.graphicsConfiguration.device.setFullScreenWindow(frame);
        frame.defaultCloseOperation = MyJFrame.HIDE_ON_CLOSE;
        frame.addWindowListener(this)
        frame.addComponentListener(this)
        frame.isResizable = false;
        this.size = frame.size;
        contentPane = ContentPane(this);
        contentPane.setLocation(0, 0);
        add(contentPane)
        Thread(contentPane).start();
        log.i("初始化完毕")
    }

    override fun start() {
        contentPane.size = size;
        contentPane.start();
    }

    fun getFrame() = frame;
    override fun close() {
        log.i("正在关闭报警窗口")
        isRuning = false;
        frame.isVisible = false;
        log.i("报警窗口已关闭.")
    }

    fun showFrame() {
        if (! frame.isVisible) {
            frame.isVisible = true;
            log.i("显示报警窗口.")
        }
        start();
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

    fun isRunning() = isRuning && cn.winfxk.lexy.z1.isRunning;
}