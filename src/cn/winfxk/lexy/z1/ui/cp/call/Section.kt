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
* Created Date: 2024/12/6  10:22 */
package cn.winfxk.lexy.z1.ui.cp.call

import cn.winfxk.lexy.z1.ui.FilletedPanel
import cn.winfxk.tool.view.toCenter
import java.awt.Color
import java.awt.Font
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import javax.swing.JLabel

abstract class Section() : FilletedPanel(), MouseListener {
    private val label = JLabel(getString().let {
        var text = "";
        for (element in it) text = text + (if (text.isBlank()) "" else "<br>") + element.toString();
        return@let "<html>$text</html>"
    });

    companion object {
        private val font = Font("楷体", Font.BOLD, 50);
    }

    init {
        cornerRadius = 40.0
        label.toCenter()
        label.font = Section.font
        label.isOpaque = true
        label.background = getColor();
        label.setLocation(0, 0)
        label.foreground = getStringColor()
        label.addMouseListener(this)
        add(label)
    }

    override fun start() {
        label.size = size;
    }
    /**
     * 背景颜色
     */
    abstract fun getColor(): Color;
    /**
     * 字体内容
     */
    abstract fun getString(): String;
    /**
     * 字体颜色
     */
    abstract fun getStringColor(): Color;
    /**
     * 点击事件
     */
    abstract fun onClick();
    override fun mouseClicked(e: MouseEvent?) {
        onClick()
    }

    override fun mousePressed(e: MouseEvent?) {
    }

    override fun mouseReleased(e: MouseEvent?) {
    }

    override fun mouseEntered(e: MouseEvent?) {
    }

    override fun mouseExited(e: MouseEvent?) {
    }
}