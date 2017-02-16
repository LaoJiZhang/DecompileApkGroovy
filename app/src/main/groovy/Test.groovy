import groovy.xml.DOMBuilder
import groovy.xml.dom.DOMCategory

class Work {

    def work() {
        println "get work done"
    }

    def analyze() {
        println "Work analyze"
    }

    def writeReport() {
        println "get report written"
    }
}


class Expert {
    def analyze() {
        println("Expert analyze")
    }
}

class Manager {
    @Delegate
    Work work = new Work()
    @Delegate
    Expert expert = new Expert()
}
//
//def bernie = new Manager()
//bernie.analyze()
//bernie.work()
//bernie.writeReport()

//what = new StringBuilder("fence")
//text = /the cow jumped over the $what/
//
//println text
//println("what = " + what)
//
//what.replace(0, 5, "moon")
//println text
//println what

//price = 684.71
//company = 'Google'
//quote = "Today ${-> company} stock closed at ${-> price}"
//println(quote)
//
//stocks = [APPLE: 663.01, Microsoft: 30.95]
//stocks.each { key, value ->
//    company = key
//    price = value
//    println("company = " + company + "  price = " + price)
//    println(quote)
//}

//pattern = /(G|g)roovy1/
//text = "Groovy in groovy ,as groovy"
////text = "groovy"
//match = text =~ pattern
//for (int i = 0; i < match.size(); i++)
//    println(match[i])

//lst = [10, 11, 12, 13, 14, 15, 16, 17, 18, 19]
//println(lst)
//subLst = lst[3..7]
//println(subLst)
//subLst[0] = 10
//println(subLst)
//println(lst)
//println(subLst.hashCode()+ "     " + lst.hashCode())
//lst.eachWithIndex { int entry, int i -> println("entry = " + entry + "   i=" + i) }

//
//map = [china: "chinese", Japan: "japanese", USA: "american"]
//println map.any { entry ->
//    entry.value =~ "[^0-9]"
//}

//thread = Thread.start {
//    startTime = System.nanoTime()
//    println("Thread started  " + startTime)
//    new Object().sleep(2000) {
//        println "interrupt thread it = " + it.dump()
//        true
//    }
//    endTime = System.nanoTime()
//    println endTime + " Thread done in ${(endTime - startTime) / 10**9} seconds"
//}
//
//println("aa = " + System.nanoTime())
//new Object().sleep(200)
//println("bb = " + System.nanoTime())
//println("Let's interrupt that thread")
//thread.interrupt()
//thread.join()

//def printThreadInfo(msg) {
//    currentThread = Thread.currentThread()
//    println("$msg Thread is ${currentThread}.  Daemon ? ${currentThread.isDaemon()}")
//}
//
//printThreadInfo("Main")
//
//Thread.start {
//    printThreadInfo("Son")
//    println(System.nanoTime())
//    sleep(3000)
//    println(System.nanoTime())
//    println("Son Thread Finished")
//}
//
//println("Main sleep befor :" + System.nanoTime())
//sleep(1000)
//println("Main sleep after :" + System.nanoTime())

document = DOMBuilder.parse(new FileReader("../res/languages.xml"))
rootElement = document.documentElement
//println(rootElement.dump())
use(DOMCategory) {
    println("println Language and author")
    languages = rootElement.language
    languages.each { language ->
        println("language = " + language.'@name' + "  author = ${language.author[0].text()}")
    }
}

println("==========================================")
documentXmlParser = new XmlParser().parse("../res/languages.xml")
documentXmlParser.each() { language ->
    println("XmlParser language = " + language.'@name' + "  author = ${language.author[0].text()}")
}

println("==========================================")
languagesXmlSlurper = new XmlSlurper().parse("../res/languages_namespace.xml").declareNamespace([computer: 'Computer'])
//languagesXmlSlurper.language.each {
//    println("XmlSlurper language = " + it.'@name' + "  author = ${it.author[0].text()}")
//}
languagesXmlSlurper.'computer:language'.each {
    println("XmlSlurper language = " + it.'@name' + "  author = ${it.author[0].text()}")
}



