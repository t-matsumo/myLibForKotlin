package library

// 2乗してオーバーフローしないギリギリの値
val INFMULT = 1 shl 15 // 32_768
val LINFMULT = 1L shl 31 // 2_147_483_648
// ２倍してオーバーフローしないギリギリの値
val INFPLUS = (1 shl 30) - 1 // 1_073_741_823
val LINFPLUS =(1L shl 62) - 1L // 4_611_686_018_427_387_903