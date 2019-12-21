package com.bridgelabz.fundoolabelmodule.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoolabelmodule.dto.LabelDTO;
import com.bridgelabz.fundoolabelmodule.model.Label;
import com.bridgelabz.fundoolabelmodule.repository.LabelRepositoryI;
import com.bridgelabz.fundoolabelmodule.response.Response;
import com.bridgelabz.fundoolabelmodule.utility.Jwt;

@Service
@PropertySource("classpath:message.properties")
@CacheConfig(cacheNames = "label")
public class LabelService implements LabelServiceI{
	
	@Autowired
	private LabelRepositoryI labelrepository;
	
	@Autowired
	private Jwt jwt;
	
	@Autowired
	private ModelMapper mapper;

	@Autowired
	private Environment labelEnvironment;
	
	
	/**
	 *Method: To Create Label
	 */
	@Cacheable(value = "CreateLabel", key = "#labeldto")
	@Override
	public Response createLabel(String token, LabelDTO labeldto) {
		
		String email = jwt.getEmailId(token);
		if (email != null) {
			
			Label label = mapper.map(labeldto, Label.class);
			label.setLabelTitle(labeldto.getLabelTitle());
			label.setEmail(email);

			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
			String date = now.format(formatter);

			label.setCreateDate(date);
			labelrepository.save(label);
			return new Response(200, labelEnvironment.getProperty("Create_Label"), labelEnvironment.getProperty("CREATE_LABEL"));
		}
		return new Response(404, labelEnvironment.getProperty("UNAUTHORIZED_USER"), null);
	}

	
	/**
	 *Method: To Update Label
	 */
	@Cacheable(value = "UpdateLabel", key = "#email")
	@Override
	public Response updateLabel(String labelid, String email, LabelDTO labeldto) {

		Label label = labelrepository.findById(labelid).get();

		if (email != null) {

			if (label.getId().equalsIgnoreCase(labelid)) {
				label.setLabelTitle(labeldto.getLabelTitle());

				LocalDateTime now = LocalDateTime.now();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
				String date = now.format(formatter);

				label.setEditDate(date);
				labelrepository.save(label);

				/* Extra Code 
				n.getLabellist().removeIf(data -> data.getId().equals(labelid));
				n.getLabellist().add(label);
				noterepository.save(n);
					
				User user = userrepository.findByEmail(email);
				user.getNotelist().removeIf(data -> data.getId().equals(n.getId()));
				user.getNotelist().add(n);
				userrepository.save(user);
				*/
				return new Response(200, labelEnvironment.getProperty("Update_Label"), labelEnvironment.getProperty("UPDATE_LABEL"));
			}
			return new Response(404, labelEnvironment.getProperty("LABEL_ID_NOT_FOUND"), null);
		}
		return new Response(404, labelEnvironment.getProperty("UNAUTHORIZED_USER"), null);
	}

	
	/**
	 *Method: To Delete Label
	 */
	@Cacheable(value = "DeleteLabel", key = "#labelId")
	@Override
	public Response deleteLabel(String labelId, String email) {

		Label label = labelrepository.findById(labelId).get();
		if (email != null) {

			if (label.getId().equalsIgnoreCase(labelId)) {
				labelrepository.deleteById(labelId);

				/* Extra Code 
				List<Note> notelist = noteService.showNotes();
				Note note = notelist.stream().filter(data -> data.getEmailId().equals(email)).findAny().orElse(null);
				n.getLabellist().remove(label);
				noterepository.save(n);

				User user = userrepository.findByEmail(email);
				user.getNotelist().removeIf(data -> data.getId().equals(n.getId()));
				user.getNotelist().add(n);
				userrepository.save(user);
				*/
				return new Response(200, labelEnvironment.getProperty("Delete_Label"), labelEnvironment.getProperty("DELETE_LABEL"));
			}
			return new Response(404, labelEnvironment.getProperty("LABEL_ID_NOT_FOUND"), null);
		}
		return new Response(404, labelEnvironment.getProperty("UNAUTHORIZED_USER"), null);
	}

	
	/**
	 *Method: To Find Label By Id
	 */
	@Cacheable(value = "FindLabel", key = "#labelId")
	@Override
	public Response findLabelById(String labelId, String email) {
		
		Label label = labelrepository.findById(labelId).get();
		
		if(email != null) {
			return new Response(200, labelEnvironment.getProperty("Find_Label"), label);
		}
		return new Response(404, labelEnvironment.getProperty("UNAUTHORIZED_USER"), null);
	}
}