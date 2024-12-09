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
* Created Date: 2024/12/5  13:20 */
package cn.winfxk.lexy.z1.ui.cp

import cn.winfxk.lexy.z1.ui.cp.call.IE
import cn.winfxk.lexy.z1.ui.cp.call.IQC
import cn.winfxk.lexy.z1.ui.cp.call.Technology
import cn.winfxk.lexy.z1.ui.cp.call.Warehouse
import cn.winfxk.tool.view.MyJPanel

class ContentPane : MyJPanel() {
    private val technology = Technology();
    private val ie = IE();
    private val warehouse = Warehouse();
    private val iqc = IQC();

    init {
        add(technology);
        add(ie);
        add(warehouse);
        add(iqc);
    }

    override fun start() {
        val itemWidth = width / 8;
        val itemHeighe = height / 5 * 4;
        val itemX = (width - itemWidth * 4) / 5;
        val itemY = height / 2 - itemHeighe / 2;
        technology.setSize(itemWidth, itemHeighe);
        technology.setLocation(itemX, itemY);
        technology.start();
        ie.size = technology.size;
        ie.setLocation(technology.x + technology.width + itemX, technology.y);
        ie.start();
        warehouse.size = technology.size;
        warehouse.setLocation(ie.x + ie.width + itemX, ie.y);
        warehouse.start();
        iqc.size = technology.size;
        iqc.setLocation(warehouse.x + warehouse.width + itemX, warehouse.y);
        iqc.start();
    }
}