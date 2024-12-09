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
* Created Date: 2024/12/7  14:51 */
package cn.winfxk.lexy.z1.ui.cp.call.to

import cn.winfxk.lexy.z1.Deploy
import cn.winfxk.tool.view.MyJPanel
import cn.winfxk.tool.view.toCenter
import java.awt.Color
import java.awt.Font
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import javax.swing.JLabel

class ContentPane(private val main: Callto) : MyJPanel(), MouseListener {
    private val label = JLabel("启动中..");
    private val hint = JLabel("点击窗口终止呼叫.");

    companion object {
        private val font = Deploy.fonts.hwct.deriveFont(Font.BOLD, 40f);
        private val hintFont = Deploy.fonts.hwct.deriveFont(Font.BOLD, 20f);
    }

    init {
        label.isOpaque = false;
        label.font = ContentPane.font;
        label.toCenter();
        label.setLocation(0, 0)
        add(label)
        hint.isOpaque = false;
        hint.font = hintFont;
        hint.toCenter();
        add(hint)
        addMouseListener(this)
    }

    override fun start() {
        label.size = size;
        hint.setSize(width, hintFont.size * 2);
        hint.setLocation(0, height - hint.height - 5);
    }

    fun setFontColor(fg: Color) {
        label.foreground = fg;
        hint.foreground = fg;
    }

    fun setString(title: String) {
        label.text = title
    }

    override fun mouseClicked(e: MouseEvent?) {
        main.close();
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