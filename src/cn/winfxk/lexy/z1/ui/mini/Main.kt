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
* Created Date: 2024/12/3  14:13 */
package cn.winfxk.lexy.z1.ui.mini

import cn.winfxk.lexy.z1.Deploy
import cn.winfxk.lexy.z1.ui.GUI
import cn.winfxk.libk.log.Log
import cn.winfxk.libk.tool.Tool
import cn.winfxk.tool.view.MyJFrame
import cn.winfxk.tool.view.MyJPanel
import cn.winfxk.tool.view.toCenter
import com.sun.awt.AWTUtilities
import java.awt.Font
import java.awt.Window
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.awt.event.MouseMotionListener
import java.awt.geom.RoundRectangle2D
import javax.swing.JLabel


class Main : MyJPanel(), MouseListener, MouseMotionListener, AutoCloseable {
    private val frame = MyJFrame();
    private var dragged = Dragged(this)
    private val log = Log(this.javaClass.simpleName)
    private val context = JLabel("<html>点击<br>呼叫</html>");
    private val iconSize = IconSize.getSize();
    private val iconFont = Deploy.fonts.hwct.deriveFont(Font.BOLD, iconSize.fontSize);

    companion object {
        private val deploy = Deploy.deploy.also { it.init() };
        private lateinit var main: Main;
        fun getMain() = main;
    }

    init {
        log.i("初始化常驻图标")
        main = this;
        frame.setSize(iconSize.width, iconSize.height);
        frame.contentPane = this;
        frame.isUndecorated = true;
        frame.type = Window.Type.UTILITY
        frame.title = deploy.Title;
        frame.iconImage = deploy.getIcon();
        frame.isAlwaysOnTop = true;
        frame.toFront();
        frame.opacity = 0f;
        frame.addComponentListener(this)
        frame.font = iconFont;
        frame.defaultCloseOperation = MyJFrame.DO_NOTHING_ON_CLOSE;
        val w = frame.width.toDouble();
        val h = frame.height.toDouble();
        AWTUtilities.setWindowShape(frame, RoundRectangle2D.Double(0.0, 0.0, w, h, w, h))
        frame.setLocation(deploy.config.getInt("IconX", (deploy.screenSize.width / 2 - w / 2).toInt()), deploy.config.getInt("IconY", (deploy.screenSize.height / 2 - h / 2).toInt()))
        this.isOpaque = false;
        this.font = iconFont;
        context.setLocation(0, 0);
        context.isOpaque = true;
        context.addMouseListener(this)
        context.addMouseMotionListener(this)
        context.font = iconFont;
        context.toCenter();
        add(context)
        IconColor(context).start();
    }

    override fun start() {
        context.size = size;
    }

    fun showWindow() {
        if (! frame.isVisible) {
            frame.isVisible = true;
            Thread {
                for (alpha in 1 .. 90) {
                    frame.opacity = alpha / 100f;
                    Tool.sleep(3);
                }
                frame.title = deploy.Title;
                log.i("常驻图标初始化并显示完毕.")
            }.start();
        }
        start();
    }

    override fun mouseClicked(e: MouseEvent?) {
        GUI.getMain().showFrame();
    }

    override fun mousePressed(e: MouseEvent?) {
        if (e == null) return;
        dragged.click = true;
        dragged.clickX = e.x;
        dragged.clickY = e.y;
    }

    override fun mouseReleased(e: MouseEvent?) {
        if (e == null) return;
        dragged.click = false;
    }

    override fun mouseEntered(e: MouseEvent?) {
        dragged.enter = true;
    }

    override fun mouseExited(e: MouseEvent?) {
        dragged.enter = false;
    }

    override fun mouseDragged(e: MouseEvent?) {
        if (e == null) return;
        var y = e.yOnScreen - dragged.clickY;
        var x = e.xOnScreen - dragged.clickX;
        if (y <= 0) y = 0;
        if (x <= 0) x = 0;
        val newX = x + frame.width;
        val newY = y + frame.height;
        if (newX >= deploy.screenSize.width) x = deploy.screenSize.width - frame.width;
        if (newY >= deploy.screenSize.height) y = deploy.screenSize.height - frame.height;
        frame.setLocation(x, y);
    }

    override fun mouseMoved(e: MouseEvent?) {
    }

    override fun close() {
        log.i("正在关闭常驻图标.")
        deploy.config.also {
            it.set("IconX", frame.x);
            it.set("IconY", frame.y);
        }
        log.i("常驻图标已关闭！")
    }
}
