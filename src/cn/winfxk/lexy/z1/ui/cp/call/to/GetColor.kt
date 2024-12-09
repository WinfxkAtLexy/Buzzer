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
* Created Date: 2024/12/7  15:53 */
package cn.winfxk.lexy.z1.ui.cp.call.to

import cn.winfxk.libk.tool.Tool
import java.awt.Color

class GetColor(color: Color) {
    private var r = color.red;
    private var g = color.green;
    private var b = color.blue;
    private var isRedAdd = true;
    private var isGreenAdd = true;
    private var isBlueAdd = true;

    companion object {
        private const val rand = 5;
        private const val rand2 = 100;
    }

    fun start() {
        if (Tool.getRand(1, rand2) == 2) isRedAdd = ! isRedAdd;
        var item = if (isRedAdd) 1 else - 1;
        r += item * Tool.getRand(1, rand);
        if (r > 255 || r < 0) {
            r = Tool.getMath(255, 0, r);
            isRedAdd = ! isRedAdd;
        }
        if (Tool.getRand(1, rand2) == 2) isGreenAdd = ! isGreenAdd;
        item = if (isGreenAdd) 1 else - 1;
        g += item * Tool.getRand(1, rand);
        if (g > 255 || g < 0) {
            g = Tool.getMath(255, 0, g);
            isGreenAdd = ! isGreenAdd;
        }
        if (Tool.getRand(1, rand2) == 2) isBlueAdd = ! isBlueAdd;
        item = if (isBlueAdd) 1 else - 1;
        b += item * Tool.getRand(1, rand);
        if (b > 255 || b < 0) {
            b = Tool.getMath(255, 0, b);
            isBlueAdd = ! isBlueAdd;
        }
    }

    fun getColor() = Color(r, g, b);
    fun getForeground() = Color(255 - r, 255 - g, 255 - b);
}