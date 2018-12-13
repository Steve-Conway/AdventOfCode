import java.io.File

fun readIntInputData(fileName: String) : List<Int> {
    val data: MutableList<Int> = mutableListOf()
    File(fileName).forEachLine {
        val intData = it.toIntOrNull()
        if (intData != null) {
            data.add(intData)
        }
    }
    return data.toList()
}

fun readStringInputData(fileName: String) : List<String> {

    val data: MutableList<String> = mutableListOf()

    File(fileName).forEachLine {

        if (it.isNotBlank()) {
            data.add(it)
        }
    }
    return data.toList()
}