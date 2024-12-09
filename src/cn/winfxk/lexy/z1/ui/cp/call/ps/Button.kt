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
* Created Date: 2024/12/7  10:09 */
package cn.winfxk.lexy.z1.ui.cp.call.ps

import cn.winfxk.lexy.z1.ui.FilletedPanel
import cn.winfxk.lexy.z1.ui.cp.call.to.Callto
import cn.winfxk.tool.view.toCenter
import java.awt.Font
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import javax.swing.JLabel

class Button(val string: String, private val main: ContentPane) : FilletedPanel(), MouseListener {
    companion object {
        private val font = Font("楷体", Font.BOLD, 30);
    }

    private val label = JLabel(string);

    init {
        label.toCenter();
        label.font = font;
        label.background = main.select.getColor();
        label.foreground = main.select.getStringColor();
        label.text = string;
        label.setLocation(0, 0)
        label.isOpaque = true;
        label.font = Button.font;
        label.addMouseListener(this)
        add(label);
    }

    override fun start() {
        label.size = size;
    }

    override fun mouseClicked(e: MouseEvent?) {
        Callto(string, main.select).showFrame();
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