package parser

import java.util.Scanner
import java.net.URL

object Parser extends App {
	val scanner = new Scanner(new URL("???").openStream());
    while(scanner.hasNextLine()){
      val line = scanner.nextLine()
      if(line.contains("chstposition"))
        println(line);
    }
    scanner.close();
}