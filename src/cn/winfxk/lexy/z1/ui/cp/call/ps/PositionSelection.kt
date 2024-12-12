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
* Created Date: 2024/12/7  09:52 */
package cn.winfxk.lexy.z1.ui.cp.call.ps

import cn.winfxk.lexy.z1.Deploy.Companion.deploy
import cn.winfxk.lexy.z1.ui.GUI
import cn.winfxk.lexy.z1.ui.cp.call.Section
import cn.winfxk.libk.log.Log
import cn.winfxk.tool.view.MyJFrame
import cn.winfxk.tool.view.MyJPanel
import com.sun.awt.AWTUtilities
import java.awt.Color
import java.awt.event.WindowEvent
import java.awt.event.WindowListener
import java.awt.geom.RoundRectangle2D

class PositionSelection(private val main: Section) : MyJPanel(), WindowListener, AutoCloseable {
    private val frame = MyJFrame();
    private val tip = main.getString()
    private val log = Log("${main.javaClass.simpleName} - ${this.javaClass.simpleName}")
    private val closeview: Closeview
    private val contentPane: ContentPane

    init {
        log.i("正在构建传呼页面")
        closeview = Closeview(this);
        contentPane = ContentPane(this, main);
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
    }

    override fun start() {
        closeview.setSize(width, 24);
        closeview.start();
        contentPane.setSize(width, height - closeview.height);
        contentPane.setLocation(0, closeview.height);
        contentPane.start();
    }

    fun showFrame() {
        frame.title = tip
        if (! frame.isVisible) {
            frame.isVisible = true;
            log.i("显示传呼选择页面")
        }
        frame.toFront();
        frame.requestFocus();
    }

    override fun windowOpened(e: WindowEvent?) {
    }

    override fun windowClosing(e: WindowEvent?) {
        log.i("关闭传呼选择页面")
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

    override fun close() {
        log.i("关闭传呼选择窗口")
        frame.isVisible = false;
        frame.dispose();
        GUI.getMain().showFrame();
    }
}