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
* Created Date: 2024/12/5  13:23 */
package cn.winfxk.lexy.z1.ui

import cn.winfxk.tool.view.MyJPanel
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.RenderingHints
import java.awt.geom.RoundRectangle2D

abstract class FilletedPanel : MyJPanel() {
    var cornerRadius = 20.0

    init {
        background = Color.white
        isOpaque = true;
    }
    override fun paint(g: Graphics) {
        if (g is Graphics2D) g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        g.clip = RoundRectangle2D.Double(0.0, 0.0, size.width.toDouble(), size.height.toDouble(), cornerRadius, cornerRadius);
        super.paint(g);
    }
}