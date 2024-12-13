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
* Created Date: 2024/12/5  13:19 */
package cn.winfxk.lexy.z1.ui.setting

import cn.winfxk.lexy.z1.Deploy.Companion.deploy
import cn.winfxk.lexy.z1.ui.GUI
import cn.winfxk.lexy.z1.ui.GUI.Companion.backg
import cn.winfxk.libk.log.Log
import cn.winfxk.libk.tool.Tool
import cn.winfxk.tool.view.MyJFrame
import cn.winfxk.tool.view.MyJPanel
import java.awt.event.WindowEvent
import java.awt.event.WindowListener

class SettingUI private constructor() : MyJPanel(), AutoCloseable, WindowListener {
    val log = Log(this.javaClass.simpleName)
    private val frame: MyJFrame;
    private val uiTItle = "设置";
    private val topView: Closeview;
    val contentPane: ContentPane;
    private val saveview: Saveview;

    companion object {
        private val main = SettingUI();
        fun getMain() = main;
    }

    init {
        log.i("初始化设置界面中...");
        frame = MyJFrame();
        frame.size = GUI.getMain().frame.size;
        frame.iconImage = deploy.getIcon();
        frame.background = backg;
        frame.setSize(GUI.width, GUI.height);
        frame.setLocation((deploy.screenSize.width / 2 - frame.width / 2).coerceAtLeast(0), (deploy.screenSize.height / 2 - frame.height / 2).coerceAtLeast(0));
        frame.contentPane = this;
        frame.defaultCloseOperation = MyJFrame.DO_NOTHING_ON_CLOSE;
        frame.addWindowListener(this)
        frame.addComponentListener(this)
        frame.title = uiTItle;
        topView = Closeview(this)
        topView.setLocation(0, 0);
        add(topView);
        contentPane = ContentPane(this);
        add(contentPane);
        saveview = Saveview(this);
        add(saveview)
        log.i("设置界面初始化完毕！")
    }

    override fun start() {
        topView.setSize(width, 24);
        topView.start();
        saveview.setSize(width, 50);
        saveview.setLocation(0, height - saveview.height - 3);
        saveview.start();
        contentPane.setSize(width, saveview.y - (topView.height + topView.y))
        contentPane.setLocation(0, topView.height + topView.x)
        contentPane.start();
    }

    override fun close() {
        log.i("设置界面隐藏")
        frame.isVisible = false;
        GUI.getMain().showFrame();
    }

    fun showFrame() {
        frame.title = uiTItle;
        if (! frame.isVisible) {
            frame.isVisible = true;
            log.i("显示设置界面")
            Thread {
                Tool.sleep(500);
                start();
                updateUI();
                repaint();
                revalidate();
            }.start();
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
}