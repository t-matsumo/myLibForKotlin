package library

// 尺取法
fun twoPointers() {
    val s = "some sequence"
    // lを動かすとlが外れる
    var r = 0 // rを動かすとrが区間に含まれる
    for (l in s.indices) {
        // rを動かす条件を&&の後ろに書く
        while (r < s.length && (s[r] == 'a')) {
            // rを追加するときの処理をかく
            r++
        }

        // rが最右に来たときの処理があればかく

        if (l == r) {
            r++ // lが追いついたら区間長が0になるので、rも一つすすめる
        } else {
            // lを除外するときの処理をかく
        }
    }
}