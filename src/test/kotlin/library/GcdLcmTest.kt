package library

import kotlin.test.Test
import kotlin.test.assertEquals


import library.gcd

class GcdLcmTest {
     @Test fun testGcd() {
          assertEquals(gcd(10, 5), 5)
     }
}