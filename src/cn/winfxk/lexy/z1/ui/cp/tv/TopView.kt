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
* Created Date: 2024/12/5  13:27 */
package cn.winfxk.lexy.z1.ui.cp.tv

import cn.winfxk.lexy.z1.Deploy.Companion.deploy
import cn.winfxk.lexy.z1.ui.GUI
import cn.winfxk.libk.tool.Tool
import cn.winfxk.tool.view.MyJPanel
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.awt.event.MouseMotionListener

class TopView(private val main: GUI) : MyJPanel(), MouseMotionListener, MouseListener {
    private val titleView = TitleView();
    private val iconView = IconView(main);
    private val dragged = Dragged();

    init {
        titleView.addMouseListener(this)
        titleView.addMouseMotionListener(this)
        add(titleView);
        iconView.setLocation(0, 0)
        add(iconView)
    }

    override fun start() {
        titleView.setSize(Tool.getMath(400, 250, width / 2), Tool.getMath(100, 40, height / 2));
        titleView.setLocation(width / 2 - titleView.width / 2, height / 2 - titleView.height / 2);
        titleView.start();
        iconView.setSize(width, 24);
        iconView.start();
    }

    override fun mouseDragged(e: MouseEvent?) {
        if (e == null) return;
        var y = e.yOnScreen - dragged.clickY - titleView.y;
        var x = e.xOnScreen - dragged.clickX - titleView.x;
        if (y <= 0) y = 0;
        if (x <= 0) x = 0;
        val newX = x + main.frame.width;
        val newY = y + main.frame.height;
        if (newX >= deploy.screenSize.width) x = deploy.screenSize.width - main.frame.width;
        if (newY >= deploy.screenSize.height) y = deploy.screenSize.height - main.frame.height;
        main.frame.setLocation(x, y);
    }

    override fun mouseMoved(e: MouseEvent?) {
    }

    override fun mouseClicked(e: MouseEvent?) {
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
}