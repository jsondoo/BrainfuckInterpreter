package test;

import model.BFInterpreter;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

public class BFInterpreterTest {
    private BFInterpreter bf;

    @Before
    public void runBefore() {
        bf = new BFInterpreter();
    }

    @Test
    public void testInvalidInputEmptyString() {
        try {
            bf.setString("");
            fail();
        } catch (IOException e) {
        }
    }


    @Test
    public void testInvalidInputSemicolon() {
        try {
            bf.setString(";");
            fail();
        } catch (IOException e) {
        }
    }

    @Test
    public void testInvalidInputColon() {
        try {
            bf.setString(":");
            fail();
        } catch (IOException e) {
        }
    }

    @Test
    public void testInvalidInputLetter() {
        try {
            bf.setString("f");
            fail();
        } catch (IOException e) {
        }
    }

    @Test
    public void testInvalidInputMultipleCharacters() {
        try {
            bf.setString("bananas!");
            fail();
        } catch (IOException e) {
        }
    }

    @Test
    public void testInvalidInputMultipleCharacters2() {
        try {
            bf.setString("5+5-2");
            fail();
        } catch (IOException e) {
        }
    }

    @Test
    public void testInvalidInputMixed() {
        try {
            bf.setString("++++a--.");
            fail();
        } catch (IOException e) {
        }
    }

    @Test
    public void testInvalidInputMixed2() {
        try {
            bf.setString("++++[>+++++ +++++/<-] --.");
            fail();
        } catch (IOException e) {
        }
    }

    @Test
    public void testInvalidInputMissingClosingBracket() {
        try {
            bf.setString("+++++++[+++");
            fail();
        } catch (IOException e) {
        }
    }

    @Test
    public void testInvalidInputMissingOpeningBracket() {
        try {
            bf.setString("+++++++]+++");
            fail();
        } catch (IOException e) {
        }
    }

    @Test
    public void testInvalidInputMissingMultipleClosingBracket() {
        try {
            bf.setString("++++++[[[[<>--+[+++");
            fail();
        } catch (IOException e) {
        }
    }

    @Test
    public void testInvalidInputMissingMultipleOpeningBracket() {
        try {
            bf.setString(">>>+++]]]+");
            fail();
        } catch (IOException e) {
        }
    }

    @Test
    public void testInvalidInputMissingMatchingBracket() {
        try {
            bf.setString(">>>+[[++]]]+");
            fail();
        } catch (IOException e) {
        }
    }

    @Test
    public void testInvalidInputMissingMultipleMatchingBrackets() {
        try {
            bf.setString(">[>>+++++[+++[+]]] [+>+++++++++-.");
            fail();
        } catch (IOException e) {
        }
    }

    @Test
    public void testSpacesRemovedFromInput() {
        String str = "+ + + + + <>> +";
        try {
            bf.setString(str);
        } catch (IOException e) {
            fail();
        }
        assertEquals("+++++<>>+", bf.getInput());
    }

    @Test
    public void testInputTrimLeadingSpaces() {
        String str = "    +++++++++++++++++<>+++----.";
        try {
            bf.setString(str);
        } catch (IOException e) {
            fail();
        }
        assertEquals("+++++++++++++++++<>+++----.", bf.getInput());
    }

    @Test
    public void testInputTrimTrailingSpaces() {
        String str = "++----.   ";
        try {
            bf.setString(str);
        } catch (IOException e) {
            fail();
        }
        assertEquals("++----.", bf.getInput());
    }

    @Test
    public void testInputTrimTab() {
        String str = "\t+++++++++++++++++<>+++----.";
        try {
            bf.setString(str);
        } catch (IOException e) {
            fail();
        }
        assertEquals("+++++++++++++++++<>+++----.", bf.getInput());
    }

    @Test
    public void testInputWithMultipleWhitespaces() {
        String str = "\t\t + + + + ++++ ++ + ++     ++++< >+ ++- ---.           ";
        try {
            bf.setString(str);
        } catch (IOException e) {
            fail();
        }
        assertEquals("+++++++++++++++++<>+++----.", bf.getInput());
    }

    @Test
    public void testPlusOperationOne() {
        String str = "+";
        try {
            bf.setString(str);
        } catch (IOException e) {
            fail();
        }
        bf.interpret();
        assertEquals("", bf.getOutput());
    }

    @Test
    public void testPlusOperationMultiple() {
        String str = "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++.";
        try {
            bf.setString(str);
        } catch (IOException e) {
            fail();
        }
        bf.interpret();
        assertEquals("I", bf.getOutput());
    }

    @Test
    public void testPlusOperationMultiple2() {
        String str = "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++.+.";
        try {
            bf.setString(str);
        } catch (IOException e) {
            fail();
        }
        bf.interpret();
        assertEquals("HI", bf.getOutput());
    }

    @Test
    public void testPlusOperationMultiple3() {
        String str = "+++++++++++++++++++++++++++++++++.+++++++++++++++++++++++++++++++++++++.+.+.";
        try {
            bf.setString(str);
        } catch (IOException e) {
            fail();
        }
        bf.interpret();
        assertEquals("!FGH", bf.getOutput());
    }

    @Test
    public void testPlusOperationMultiple4() {
        String str = "....+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++.";
        try {
            bf.setString(str);
        } catch (IOException e) {
            fail();
        }
        bf.interpret();
        assertEquals("C", bf.getOutput());
    }

    @Test
    public void testMinusOperationMixed() {
        String str = "+++++++++++++++++++++++++++++++++++++++++++++---------."; // 36
        try {
            bf.setString(str);
        } catch (IOException e) {
            fail();
        }
        bf.interpret();
        assertEquals("$", bf.getOutput());

    }

    @Test
    public void testMinusOperationMultiple() {
        String str = "---------------------------------------------------------------------------------------------------" +
                "--------------------------------------------.";
        try {
            bf.setString(str);
        } catch (IOException e) {
            fail();
        }
        bf.interpret();
        assertEquals("q", bf.getOutput());
    }

    @Test
    public void testMinusOperationNegativeASCII() {
        String str = "-----------.";
        try {
            bf.setString(str);
        } catch (IOException e) {
            fail();
        }
        bf.interpret();
        assertEquals("\uFFF5", bf.getOutput());
    }

    @Test
    public void testShiftOperationSimple() {
        String str = "+-+>++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++.";
        try {
            bf.setString(str);
        } catch (IOException e) {
            fail();
        }
        bf.interpret();
        assertEquals("J", bf.getOutput());
    }

    @Test
    public void testShiftOperationSimple2() {
        String str = "+-+>>>><<><>>++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++.";
        try {
            bf.setString(str);
        } catch (IOException e) {
            fail();
        }
        bf.interpret();
        assertEquals("J", bf.getOutput());
    }

    @Test
    public void testShiftOperationSimple3() {
        String str =
                ">++++++++++++++++++++++++++++++++++++++++++++++++++++<+++++++++++++++++++++++++++++++++++++++++++++---------.>++++++++++.";
        try {
            bf.setString(str);
        } catch (IOException e) {
            fail();
        }
        bf.interpret();
        assertEquals("$>", bf.getOutput());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testShiftOperationOutOfBounds() {
        String str = "<.";
        try {
            bf.setString(str);
        } catch (IOException e) {
            fail();
        }
        bf.interpret();
        fail();
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testShiftOperationOutOfBoundsMultipleOperations() {
        String str = ">>+>++>-<<<<<<.";
        try {
            bf.setString(str);
        } catch (IOException e) {
            fail();
        }
        bf.interpret();
        fail();
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testShiftOperationOutOfBoundsMultipleOperations2() {
        String str = "<<<++";
        try {
            bf.setString(str);
        } catch (IOException e) {
            fail();
        }
        bf.interpret();
        fail();
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testShiftOperationOutOfBoundsMultipleOperations3() {
        String str = ">+++++++.<<-";
        try {
            bf.setString(str);
        } catch (IOException e) {
            fail();
        }
        bf.interpret();
        fail();
    }

    // Tests for loops
    @Test
    public void testSingleLoop() {
        String str = "+++++ [>++++++++++<-] >+++++.";
        try {
            bf.setString(str);
        } catch (IOException e) {
            fail();
        }
        bf.interpret();
        assertEquals("7", bf.getOutput());
    }

    @Test
    public void testSingleLoop2() {
        String str = "++++++ [>++++++++++<-] >+++++.";
        try {
            bf.setString(str);
        } catch (IOException e) {
            fail();
        }
        bf.interpret();
        assertEquals("A", bf.getOutput());
    }

    @Test
    public void testNestedLoopHelloWorld() {
        String str = "++++++++++[>+++++++>++++++++++>+++>+<<<<-]>++.>+.+++++++..+++.>++.<<+++++++++++++++.>.+++" +
                ".------.--------.>+.>.";
        try {
            bf.setString(str);
        } catch (IOException e) {
            fail();
        }
        bf.interpret();
        assertEquals("Hello World!", bf.getOutput());
    }

    @Test
    public void testNestedLoopHappyBirthday() {
        String str = "-[------->+<]>-.[--->++++<]>+.-[++>-----<]>..+++++++++.-[---->+<]>++.[->+++<]>++.+++++++" +
                ".+++++++++.++.------------.----.---.+[--->+<]>+++.-[---->+<]>++.---[->++<]>.++[--->++<]>+.";
        try {
            bf.setString(str);
        } catch (IOException e) {
            fail();
        }
        bf.interpret();
        assertEquals("Happy birthday :)", bf.getOutput());
    }

    // Test more complex inputs
    @Test
    public void testLongInput() {
        String str = "-[--->+<]>-.[---->+++++<]>-.+.++++++++++.+[---->+<]>+++.-[--->++<]>-.++++++++++" +
                ".+[---->+<]>+++.[->+++<]>+.-[->+++<]>.---[----->++<]>.-------------.----.++++++++++" +
                "+..-[--->+<]>.-[---->+<]>++.++[--->++<]>.+++.-.-------.-[--->+<]>--.---[->++++<]>-.++++" +
                "[->+++<]>.+++++++++.++++++.+++[->+++<]>.+++++++++.-----------.++.-[->+++<]>.------------.+++" +
                "+[->++<]>+.-[->++++<]>.-[--->++<]>--.+++++++.+.-----------.--[--->+<]>-.+[----->+<]>.--[--->" +
                "+<]>.-[---->+<]>++.-[--->++<]>-.+++++.++++++.+++[->+++<]>.+++++++++++++.--.-----------.+++++" +
                "++++++++.++.+++[->+++<]>.+++++++++++++.[-->+++++<]>+++.+[->+++<]>+.+++++++++++.----------.[-" +
                "-->+<]>----.+[---->+<]>+++.+[----->+<]>+.+.+++++.[---->+<]>+++.[->+++<]>++.[--->+<]>----.---" +
                "----------.----.++++++++++.[->++++++++++<]>.";
        try {
            bf.setString(str);
        } catch (IOException e) {
            fail();
        }
        bf.interpret();
        assertEquals("This is a really long sentence, I hope my interperter does not break.", bf.getOutput());
    }

    @Test
    public void testLongerInput() {
        String str = "-[--->+<]>-.[---->+++++<]>-.---.--[--->+<]>-.+[----->+<]>.--------.++++++++.-----------.+++" +
                ".+++++++++++++.[-->+++++<]>+++.---[----->++<]>.---.+++++.-.+[---->+<]>+++.---[->++++<]>.------------" +
                ".---.--[--->+<]>-.+[->+++<]>++.+++++++.-------.+++++++++++.--------.-------.+++++++++++++.++++++" +
                ".[---->+<]>+++.[->+++<]>+.++.-[--->+<]>----.---.++++..+[---->+<]>+++.[->+++<]>+.+++++++++++++" +
                ".-[->+++++<]>-.[->+++<]>+.+.[--->+<]>-.--.----------.[->++++++<]>.+[->+++<]>.--[--->+<]>-" +
                ".[-->+++++++<]>.-------.+++++++++++.+++[->+++<]>+.-----.+++++++++++..[-->+<]>--------.++[--->++<]>" +
                ".>-[--->+<]>-.[---->+++++<]>-.---.--[--->+<]>-.---[----->++<]>.+++[->+++<]>++.++++++.-[--->+<]>--" +
                ".++[->+++<]>.++++++.+++.++++++++.[->+++<]>.+++++++++++++.+.+[---->+<]>+++.[->+++<]>+.++++++.------" +
                ".++++++++.+++++.+++++.+.[---->+<]>+++.---[->++++<]>.------------.---.--[--->+<]>-.---[->++++<]>-" +
                ".----------.-[--->+<]>.[---->+<]>++.-[--->++<]>--.+++++++.+.-----------.[->+++<]>-.++[--->++<]>" +
                ".[->++<]>+.-[-->+<]>.+[->+++<]>+.---.+[--->+<]>+++.-[---->+<]>++.[->+++<]>++.++++++++++.---.+++++" +
                ".---.++++++++.[->+++++<]>.+[-->+<]>.[->++<]>+.-[-->+<]>.+[->+++<]>.++++++++++++.+++++++.+[->+++<]>" +
                ".+++++++++++++.-------------.-.-[--->+<]>-.+[->+++<]>.+++++.[--->+<]>---.-------.---.-[++>---<]>+" +
                ".---[->++++<]>-.+.+[->+++<]>++.++++++++++++.+++.+++.+[++>---<]>.++[--->++<]>.>-[--->+<]>-" +
                ".[---->+++++<]>-.---.--[--->+<]>-.--[->++++<]>-.--------.+++.----.-[->+++++<]>-.++[->+++<]>.+++" +
                ".+++++.-------.--.+++++++++++++.[-->+++++<]>+++.[->+++<]>+.+[--->+<]>+.-[->+++<]>-.++++++++" +
                ".+++++++++++.-.+[---->+<]>+++.---[->++++<]>.------------.---.--[--->+<]>-.+[->+++<]>.++" +
                ".+++++++++++++.++.-----------.---.+++.------.--.--[--->+<]>-.+++[->+++<]>.[->+++<]>-.++[--->++<]>" +
                ".++[->++<]>+.-[----->+<]>++.+[->+++<]>.+++++++++++++.+++++++.-[---->+<]>++.+[->+++<]>+.+++++" +
                ".++++++++++.------------.--[--->+<]>--.--.+.[---->+<]>+++.---[->++++<]>-.--------.--.+++++++.+++" +
                ".+[---->+<]>+++.+[----->+<]>+.---------.----.--[--->+<]>---.[-->+++++<]>+++.[->+++<]>+.+++++++++++++" +
                ".-[->+++++<]>-.[->+++<]>+.-[++>-----<]>..[----->++<]>+.+++++++++++..-------.-.-[--->+<]>-.+[->+++<]>" +
                ".-[--->+<]>----.---------.+++++++++++.+++[->+++<]>.+++++++++++++.---------.++++++.-.[----->++<]>++.";
        try {
            bf.setString(str);
        } catch (IOException e) {
            fail();
        }
        bf.interpret();
        assertEquals("The member rots the elephant across an abusive pitfall. The rag flowers against the six hope. A" +
                " day blinks? A covered chunk stamps. The worn finger awaits the certificate. Every disgust skips " +
                "near an appalled criterion.", bf.getOutput());
    }


}
