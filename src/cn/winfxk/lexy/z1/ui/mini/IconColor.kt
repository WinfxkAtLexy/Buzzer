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
* Created Date: 2024/12/5  09:57 */
package cn.winfxk.lexy.z1.ui.mini

import cn.winfxk.lexy.z1.client.NettyClient
import cn.winfxk.lexy.z1.isRunning
import cn.winfxk.libk.tool.Tool
import java.awt.Color
import javax.swing.JLabel

class IconColor(private val icon: JLabel) : Thread() {
    private var red: Int = 255;
    private var green: Int = 255;
    private var blue: Int = 255;
    private var ra = false;
    private var ga = false;
    private var ba = false;

    init {
        icon.background = getColor();
    }

    private fun getColor() = Color(red, green, blue)
    private fun getForegroundColor() = Color(255 - red, 255 - green, 255 - blue)
    override fun run() {
        while (isRunning) {
            if (! NettyClient.getMain().isConnected()) {
                if (red >= 254 && green <=1&& blue <=1) {
                    Tool.sleep(1000);
                    continue
                }
                if (red <= 254) red ++;
                if (green >1) green --;
                if (blue >1) blue --;
                Tool.sleep(3)
            } else {
                Tool.sleep(20)
                var id = if (ra) 1 else - 1;
                red += Tool.getRand(1, 4) * id;
                if (red >= 255 || red <= 0) {
                    ra = ! ra;
                    red = Tool.getMath(255, 0, red)
                }
                id = if (ga) 1 else - 1;
                green += Tool.getRand(1, 4) * id;
                if (green >= 255 || green <= 0) {
                    ga = ! ga;
                    green = Tool.getMath(255, 0, green)
                }
                id = if (ba) 1 else - 1;
                blue += Tool.getRand(1, 4) * id;
                if (blue >= 255 || blue <= 0) {
                    ba = ! ba;
                    blue = Tool.getMath(255, 0, blue)
                }
            }
            icon.background = getColor();
            icon.foreground = getForegroundColor();
        }
    }
}