# Restore IP Addresses

**Difficulty:** Medium
**Topic:** Backtracking
**LeetCode #:** 93

## Problem Statement
A valid IP address consists of exactly four integers separated by single dots. Each integer is between `0` and `255` (inclusive) and cannot have leading zeros.

Given a string `s` containing only digits, return all possible valid IP addresses that can be formed by inserting dots into `s`. You are not allowed to reorder or remove any digits in `s`. You may return the valid IP addresses in any order.

## Examples

**Example 1:**
```
Input:  s = "25525511135"
Output: ["255.255.11.135","255.255.111.35"]
```

**Example 2:**
```
Input:  s = "0000"
Output: ["0.0.0.0"]
```

**Example 3:**
```
Input:  s = "101023"
Output: ["1.0.10.23","1.0.102.3","10.1.0.23","10.10.2.3","101.0.2.3"]
```

## Constraints
- `1 <= s.length <= 20`
- `s` consists of digits only.

## Approach
Backtracking — try placing dots after positions 1, 2, or 3 characters from the current start index.
At each step, validate the current segment:
- Length must be 1–3 characters.
- No leading zeros (segment "01", "00" are invalid; "0" alone is fine).
- Numeric value must be ≤ 255.

Recurse until 4 segments are formed. Add the candidate to results only when all 4 segments are placed **and** the entire string is consumed.

Prune early if the remaining characters can't possibly fill the remaining segments (each segment needs 1–3 chars).
