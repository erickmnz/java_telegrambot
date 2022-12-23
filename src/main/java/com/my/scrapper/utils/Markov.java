package com.my.scrapper.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class Markov {

	private static Random random = new Random();

	public static String generate(String text, Integer size, Integer outSize) throws IOException {
		if (size < 1)
			throw new IllegalArgumentException("Size can not be less than 1");

		byte[] bts = text.getBytes();

		String[] nWords = new String(bts).trim().split(" ");
		if (outSize < size || outSize >= nWords.length)
			throw new IllegalArgumentException("Output soize is out of bounds");

		Map<String, List<String>> map = new HashMap<>();

		for (int i = 0; i < (nWords.length - size); i++) {
			StringBuilder kk = new StringBuilder(nWords[i]);
			for (int j = i + 1; j < i + size; j++) {
				kk.append(" ").append(nWords[j]);
			}
			String val = (i + size < nWords.length) ? nWords[i + size] : "";

			if (!map.containsKey(kk.toString())) {
				List<String> l = new ArrayList<>();
				l.add(val);
				map.put(kk.toString(), l);

			} else {
				map.get(kk.toString()).add(val);
			}

		}
		int num = 0;
		int randNum = random.nextInt(map.size() - 1);

		String prf = map.keySet().toArray()[randNum].toString();

		List<String> outWay = new ArrayList<>(Arrays.asList(prf.split(" ")));

		while (true) {
			List<String> sfx = map.get(prf);

			if (sfx.size() == 1) {
				if (Objects.equals(sfx.get(0), ""))
					return outWay.stream().reduce("", (x, y) -> x + " " + y);
				outWay.add(sfx.get(0));
			} else {
				randNum = random.nextInt(sfx.size());
				outWay.add(sfx.get(randNum));
			}
			if (outWay.size() >= outSize)
				return outWay.stream().reduce("", (x, y) -> x + " " + y);

			num++;
			prf = outWay.stream().skip(num).limit(size).reduce("", (x, y) -> x + " " + y).trim();
		}

	}

}
