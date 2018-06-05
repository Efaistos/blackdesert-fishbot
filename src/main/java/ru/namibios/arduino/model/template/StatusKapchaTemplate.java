package ru.namibios.arduino.model.template;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ru.namibios.arduino.config.Path;
import ru.namibios.arduino.utils.MatrixUtils;

public enum StatusKapchaTemplate implements MatrixTemplate{

	/**
		Картинка постоянно меняет свою положение, потому сложно распознавать программно, гораздо проще
		обучить нейронную сеть на опознание. Но эта функция несильно нужна, поэтому как-нибудь потом.
	*/
	
	SUCCESS("SUCCESS"),
	
	FAILURE("FAILURE");
	
	private final List<int[][]> templates;
	
	@Override
	public List<int[][]> getTemplates() {
		return templates;
	}
	
	private StatusKapchaTemplate(String filename) {
		this.templates = new ArrayList<int[][]>();

		try {
			
			List<String> list = Files.lines(Paths.get(Path.STATUS_KAPCHA, filename), StandardCharsets.UTF_8)
					.collect(Collectors.toCollection(ArrayList::new));
			
			templates.add(MatrixUtils.importTemplate(list));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}