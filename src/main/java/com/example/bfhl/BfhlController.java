package com.example.bfhl;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.regex.Pattern;

@RestController
public class BfhlController {

    static class InputDTO {
        public List<Object> data;
        public String full_name; // optional, to fill user_id
        public String dob_ddmmyyyy; // optional, to fill user_id
        public String email;
        public String roll_number;
    }

    static class OutputDTO {
        public boolean is_success;
        public String user_id;
        public String email;
        public String roll_number;
        public List<String> odd_numbers;
        public List<String> even_numbers;
        public List<String> alphabets;
        public List<String> special_characters;
        public String sum;
        public String concat_string;
    }

    private static final Pattern DIGITS = Pattern.compile("^\\d+$");
    private static final Pattern ALPHAS = Pattern.compile("^[A-Za-z]+$");

    @PostMapping("/bfhl")
    public ResponseEntity<OutputDTO> bfhl(@RequestBody InputDTO input) {
        OutputDTO out = new OutputDTO();
        try {
            List<String> evens = new ArrayList<>();
            List<String> odds = new ArrayList<>();
            List<String> alphas = new ArrayList<>();
            List<String> specials = new ArrayList<>();
            long sum = 0L;

            List<String> raw = new ArrayList<>();
            if (input.data != null) {
                for (Object o : input.data) raw.add(String.valueOf(o));
            }

            // Build user_id
            // user_id pieces (used when not provided in request)
            String fullName = (input.full_name == null ? "saiteja" : input.full_name)
                    .toLowerCase().replace(" ", "_");
            String dob = (input.dob_ddmmyyyy == null ? "16092004" : input.dob_ddmmyyyy);

// combine for user_id
            out.user_id = fullName + "_" + dob;

// email & roll
            out.email = (input.email == null ? "gourusaiteja1291@gmail.com" : input.email);
            out.roll_number = (input.roll_number == null ? "22bce7009" : input.roll_number);

            StringBuilder onlyLetters = new StringBuilder();

            for (String s : raw) {
                if (DIGITS.matcher(s).matches()) {
                    // numeric
                    long n = 0;
                    try { n = Long.parseLong(s); } catch (Exception ignored) {}
                    if (n % 2 == 0) evens.add(s);
                    else odds.add(s);
                    sum += n;
                } else if (ALPHAS.matcher(s).matches()) {
                    // alphabetic token (word)
                    alphas.add(s.toUpperCase());
                    onlyLetters.append(s);
                } else {
                    // special (includes tokens with mix of letters+digits or symbols)
                    specials.add(s);
                    // still extract letters for concat rule (ALL alphabetical chars present)
                    for (char c : s.toCharArray()) {
                        if (Character.isLetter(c)) onlyLetters.append(c);
                    }
                }
            }

            // Build concat_string: reverse of all alphabetical characters, alternating caps (Upper, lower, Upper, ...)
            String letters = onlyLetters.toString();
            StringBuilder revAlt = new StringBuilder();
            for (int i = letters.length() - 1, k = 0; i >= 0; i--, k++) {
                char ch = letters.charAt(i);
                revAlt.append( (k % 2 == 0) ? Character.toUpperCase(ch) : Character.toLowerCase(ch) );
            }

            out.is_success = true;
            out.odd_numbers = odds;
            out.even_numbers = evens;
            out.alphabets = alphas;
            out.special_characters = specials;
            out.sum = String.valueOf(sum);
            out.concat_string = revAlt.toString();

            return ResponseEntity.ok(out);
        } catch (Exception e) {
            out.is_success = false;
            out.user_id = "";
            out.email = "";
            out.roll_number = "";
            out.odd_numbers = Collections.emptyList();
            out.even_numbers = Collections.emptyList();
            out.alphabets = Collections.emptyList();
            out.special_characters = Collections.emptyList();
            out.sum = "0";
            out.concat_string = "";
            return ResponseEntity.ok(out);
        }
    }
}
