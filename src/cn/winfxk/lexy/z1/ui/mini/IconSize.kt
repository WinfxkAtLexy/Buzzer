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
* Created Date: 2024/12/5  13:07 */
package cn.winfxk.lexy.z1.ui.mini

import cn.winfxk.lexy.z1.Deploy

enum class IconSize(val width: Int, val height: Int = width, val fontSize: Float = (width / 5).toFloat()) {
    Small(30),
    Medium(50),
    Large(100),
    ExtraLarge(150);

    companion object {
        fun getSize() = Deploy.deploy.config.getString("IconSize", Large.name).let {
            when (it) {
                Small.name      -> Small
                Medium.name     -> Medium
                Large.name      -> Large
                ExtraLarge.name -> ExtraLarge
                else            -> Large
            }
        }
    }
}