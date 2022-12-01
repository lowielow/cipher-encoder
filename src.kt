package chucknorris

class Software {
    
    fun printMenu() {
        
        println("Please input operation (encode/decode/exit):")
        
    }
    
    fun printMenuInputError(invalidInput: String) {
    
        println("There is no '${invalidInput}' operation")
        
    }
    
    fun encode() {

        println("Input string:")
        
        val encodeInput: String = readln()
        var encodeBinary: String = ""
        var encodeString: String = ""
        var previousChar: Char = ' '
        
        for (ch in encodeInput) {
            encodeBinary += String.format("%07d", Integer.toBinaryString(ch.code).toInt())
        }
        
        for (ch in encodeBinary) {
            when {
                ch =='1' && previousChar == ' '  -> {
                    encodeString += "0 0"
                    previousChar = '1'
                } 	
                ch =='1' && previousChar == '1' -> {
                    encodeString += "0"
                    previousChar = '1'
                }	
                ch =='1' && previousChar == '0' -> {
                    encodeString += " 0 0"
                    previousChar = '1'
                }	
                ch =='0' && previousChar == ' '  -> {
                    encodeString += "00 0"
                    previousChar = '0'
                }	
                ch =='0' && previousChar == '0' -> {
                    encodeString += "0"
                    previousChar = '0'
                }	
                ch =='0' && previousChar == '1' -> {
                    encodeString += " 00 0"
                    previousChar = '0'
                }	
            }
        }
        
        println("Encoded string:")
        println(encodeString)
        println("")
        
    }
    
    fun decode() {
        
        println("Input encoded string:")
        
        val decodeInput: String = readln()
        val decodeList = decodeInput.split(" ").map { it }.toMutableList()
        
        if (checkDecodeInput(decodeInput, decodeList) == true) {
            
            
            val twoMultiplier = mutableListOf(64, 32, 16, 8, 4, 2, 1)
            var twoIndex: Int = 0
            var decodeBinary: String = ""
            var decodeInt: Int = 0
            var decodeString: String = ""
            
            for (i in 0..decodeList.size - 2 step 2) {
                if (decodeList[i] == "0") {
                    decodeBinary += decodeList[i + 1].replace("0", "1")
                } else if (decodeList[i] == "00") {
                    decodeBinary += decodeList[i + 1]    
                }
            }
            
            for (ch in decodeBinary) {
                decodeInt += ch.toString().toInt() * twoMultiplier[twoIndex]
                twoIndex++
                if (twoIndex == 7) {
                    decodeString += decodeInt.toChar()
                    twoIndex = 0
                    decodeInt = 0
                }
            }
            
            println("Decoded string:")
            println(decodeString)
            println("")
            
        } else if (checkDecodeInput(decodeInput, decodeList) == false) {
            
            println("Encoded string is not valid.")
            
        }
        
    }
    
    fun checkDecodeInput(input: String, list: MutableList<String>): Boolean {
        
        // (1) check encoded message includes characters other than 0 or spaces
        val checkOne: Int = input.replace("0", "").replace(" ", "").length
        
        // (2) check first block of each sequence is not 0 or 00
        var checkTwo: Int = 0
        for (i in 0..list.size - 2 step 2) {
			      if (list[i] != "0" && list[i] != "00") {
                checkTwo++
            }
        }        
        
        // (3) check number of blocks is odd
        val checkThree: Int = list.size % 2
        
        // (4) check length of the decoded binary string is not a multiple of 7.
        var checkFour: Int = 0
        for (i in 1..list.size - 1 step 2) {
            checkFour += list[i].length
        } 
        checkFour %= 7
        
        when {
            checkOne == 0 && checkTwo == 0 && checkThree == 0 && checkFour == 0 -> return true
            else -> return false 
        }

        
    }
    
    fun exit() {
        
        println("Bye")
        
    }
    
}

fun main() {
    
    val software = Software()
    
    while (true) {
        software.printMenu()   
        val menuInput: String = readln()
        when (menuInput) {
            "encode" -> software.encode()
            "decode" -> software.decode()
            "exit" -> {
                software.exit()
                break
            } 
            else -> software.printMenuInputError(menuInput)
        }
    }
    
}
