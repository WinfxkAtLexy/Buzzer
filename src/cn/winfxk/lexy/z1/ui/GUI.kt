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
* Created Date: 2024/12/2  09:08 */
package cn.winfxk.lexy.z1.ui

import cn.winfxk.lexy.z1.Deploy.Companion.deploy
import cn.winfxk.lexy.z1.ui.cp.ContentPane
import cn.winfxk.lexy.z1.ui.cp.tv.TopView
import cn.winfxk.lexy.z1.ui.mini.Main
import cn.winfxk.libk.log.Log
import cn.winfxk.libk.tool.Tool
import cn.winfxk.tool.view.MyJFrame
import cn.winfxk.tool.view.MyJPanel
import com.sun.awt.AWTUtilities
import java.awt.Color
import java.awt.event.WindowEvent
import java.awt.event.WindowListener
import java.awt.geom.RoundRectangle2D

class GUI : MyJPanel(), WindowListener, AutoCloseable {
    val frame = MyJFrame();
    private val log by lazy { Log(this.javaClass.simpleName) }
    private val cp: ContentPane;
    private val topView: TopView

    companion object {
        private lateinit var main: GUI;
        private var isInitialized: Boolean = false;
        val backg = Color.white;
        /*        private val width = deploy.config.getInt("width", Tool.getMath(1600, 800, (deploy.screenSize.width / 1.2).toInt()));
                private val height = deploy.config.getInt("height", Tool.getMath(820, 600, (deploy.screenSize.height / 1.5).toInt()));*/
        val width = 900;
        val height = 600;
        fun getMain() = main;
    }

    init {
        main = this;
        log.i("初始化用户界面中..")
        cp = ContentPane();
        topView = TopView(this);
        frame.iconImage = deploy.getIcon();
        frame.background = backg;
        frame.setSize(GUI.width, GUI.height);
        frame.setLocation((deploy.screenSize.width / 2 - frame.width / 2).coerceAtLeast(0), (deploy.screenSize.height / 2 - frame.height / 2).coerceAtLeast(0));
        frame.contentPane = this;
        frame.isUndecorated = true;
        frame.addWindowListener(this)
        frame.defaultCloseOperation = MyJFrame.DO_NOTHING_ON_CLOSE;
        frame.addComponentListener(this)
        frame.isResizable = false;
        frame.title = deploy.Title;
        val w = frame.width.toDouble();
        val h = frame.height.toDouble();
        AWTUtilities.setWindowShape(frame, RoundRectangle2D.Double(0.0, 0.0, w, h, 20.0, 20.0))
        isInitialized = true;
        topView.setLocation(0, 0);
        add(topView);
        add(cp);
        log.i("用户界面初始化完毕！")
    }

    override fun start() {
        size = frame.size;
        topView.setSize(width, Tool.getMath(150, 80, height / 8));
        topView.start();
        cp.setSize(width, height - topView.height);
        cp.setLocation(0, topView.height);
        cp.start();
    }

    fun showFrame() {
        if (! isInitialized) return;
        if (! frame.isVisible) {
            frame.isVisible = true;
            log.i("显示用户主界面")
        }
        frame.title = deploy.Title;
        frame.toFront()
        start();
    }

    override fun close() {
        if (! isInitialized) return;
        frame.isVisible = false;
        frame.dispose()
        log.i("用户界面关闭")
        deploy.config.set("width", frame.width);
        deploy.config.set("height", frame.height);
        deploy.config.save();
    }

    override fun windowOpened(e: WindowEvent?) {
    }

    override fun windowClosing(e: WindowEvent?) {
        log.i("隐藏用户界面")
        if (deploy.config.getBoolean("显示报警图标", true)) Main.getMain().showWindow();
        deploy.config.set("width", frame.width);
        deploy.config.set("height", frame.height);
        deploy.config.save();
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
}