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
* Created Date: 2024/12/7  10:19 */
package cn.winfxk.lexy.z1.ui.cp.call.ps

import cn.winfxk.lexy.z1.Deploy
import cn.winfxk.lexy.z1.ui.cp.call.Section
import cn.winfxk.tool.view.MyJPanel
import java.awt.Color
import java.awt.Dimension
import java.math.BigDecimal
import java.math.RoundingMode

class ContentPane(private val main: PositionSelection, val select: Section) : MyJPanel(), AutoCloseable {
    private val call = ArrayList<Button>();

    companion object {
        private lateinit var main: ContentPane;
        private val itemWidth = BigDecimal(180);
        private val itemX = BigDecimal(20);
        val defaultlist = listOf("MES", "软管", "P01", "P02", "P03", "P04", "P05", "P06", "P07", "P08", "P09", "P10", "P11", "P12", "P13", "P14", "P15", "P16", "P17", "P18");
        private val list = ArrayList((Deploy.deploy.config.getStringList("CallButtons", defaultlist) ?: defaultlist).filter { ! it.isNullOrBlank() })
        fun getList() = list;
        fun setList(list: List<String>) {
            this.list.clear()
            this.list.addAll(list)
        }
    }

    init {
        ContentPane.main = this;
        background = Color.white;
        isOpaque = true;
        list.forEach { item ->
            if (! item.isNullOrBlank()) call.add(Button(item, this))
        }
    }

    override fun start() {
        removeAll();
        val width = itemWidth.toInt();
        val count = (BigDecimal(this.width) - itemX).divide(itemWidth + itemX, 10, RoundingMode.HALF_UP).toInt();
        val size = Dimension(width, 80);
        val x = (this.width - (count * width)) / count;
        var line = 0;
        var row = 0;
        for (index in 0 ..< call.size) call[index].also {
            if (++ row > count) {
                row = 0;
                line ++;
            }
            it.setLocation(((row * width) + (x * (row + 0.5))).toInt(), line * size.height + y * line)
            it.size = size;
            it.start();
            add(it);
        }
        updateUI()
    }

    override fun close() {
        main.close()
    }
}