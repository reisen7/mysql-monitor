package com.fc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class,scanBasePackages = "com.fc.v2")
public class V2Application {

    public static void main(String[] args) {

        SpringApplication.run(V2Application.class, args);
		System.out.println("*******************************************\n" +
				"*******************************************\n" +

				"く__,.ヘヽ.　　　　/　,ー､ 〉         \n"+
				"　　　　　＼ ', !-─‐-i　/　/´       ,-----------------------------.  \n"+
				"　　　 　 ／｀ｰ'　　　 L/／｀ヽ､     ( ------ MySQL 监控工具系统 ------ )\n"+
				"　　 　 /　 ／,　 /|　 ,　 ,　　',   `-,---------------------------`\n"+
				"　　　ｲ 　/ /-‐/　ｉ　L_ ﾊ ヽ!　 i _.-'  \n"+
				"　　　 ﾚ ﾍ 7ｲ｀ﾄ　 ﾚ'ｧ-ﾄ､!ハ|　 |     \n"+
				"　　　　 !,/7 '0'　　 ´0iソ| 　 |　　　\n"+
				"　　　　 |.从\"　　_　　 ,,,, / |./ 　 |   \n"+
				"　　　　 ﾚ'| i＞.､,,__　_,.イ / 　.i 　|   \n"+
				"　　　　　 ﾚ'| | / k_７_/ﾚ'ヽ,　ﾊ.　|    \n"+
				"　　　　　　 | |/i 〈|/　 i　,.ﾍ |　i　|   \n"+
				"　　　　　　.|/ /　ｉ： 　 ﾍ!　　＼　|  \n"+
				"　　　 　 　 kヽ>､ﾊ 　 _,.ﾍ､ 　 /､!     \n"+
				"　　　　　　 !'〈//｀Ｔ´', ＼ ｀'7'ｰr'   \n"+
				"　　　　　　 ﾚ'ヽL__|___i,___,ンﾚ|ノ    \n"+
				"　　　　　 　　　ﾄ-,/　|___./           \n"+
				"　　　　　 　　　'ｰ'　　!_,.:             \n");


	}

}
