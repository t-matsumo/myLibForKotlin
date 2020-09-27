package library

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals

import library.gcd
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested

class GcdLcmTest {
     @Test fun testGcd() {
          assertEquals(gcd(10, 5), 5)
          assertEquals(gcd(5, 10), 5)
     }

     @Test fun testGcdWithOne() {
          assertEquals(gcd(1, 10), 1)
          assertEquals(gcd(1, 1), 1)
     }

     @Test fun testGcdWithZero() {
          // println(gcd(0, 10))
          // // assertEquals(gcd(0, 10), 0)
          // assertEquals(gcd(10, 0), 0)
          // assertEquals(gcd(0, 0), 0)
     }

     @Nested
     inner class ExtGcdTest() {
          @Test
          fun testExtGcd() {
               assertEquals(Triple(1, 2, -1).toString(), extGcd(3L, 5L).toString())
               assertEquals(Triple(2, -1, 1).toString(), extGcd(10, 12).toString())
          }
     }
}