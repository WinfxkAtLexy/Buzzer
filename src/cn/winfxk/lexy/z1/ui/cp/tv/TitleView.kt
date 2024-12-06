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
* Created Date: 2024/12/5  14:09 */
package cn.winfxk.lexy.z1.ui.cp.tv

import cn.winfxk.lexy.z1.Deploy
import cn.winfxk.lexy.z1.ui.FilletedPanel
import cn.winfxk.tool.view.toCenter
import java.awt.Color
import java.awt.Font
import javax.swing.JLabel

class TitleView : FilletedPanel() {
    private val title = JLabel(Deploy.deploy.Name);
    private val tfont = Font("楷体", Font.BOLD, 20);

    init {
        title.font = tfont;
        title.toCenter();
        title.setLocation(0, 0);
        add(title)
        background = Color(220, 220, 220);
    }

    override fun start() {
        cornerRadius = (Math.min(width, height) / 1.2).toDouble();
        title.size = size
    }
}