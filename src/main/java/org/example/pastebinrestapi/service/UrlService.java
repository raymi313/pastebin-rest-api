package org.example.pastebinrestapi.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.pastebinrestapi.dto.UrlLongDto;
import org.example.pastebinrestapi.entities.UrlEntity;
import org.example.pastebinrestapi.repositories.UrlRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UrlService {
    private final UrlRepository urlRepository;
    private final BaseConversion conversion;

    public UrlService(UrlRepository urlRepository, BaseConversion baseConversion) {
        this.urlRepository = urlRepository;
        this.conversion = baseConversion;
    }

    public String convertToShortUrl(UrlLongDto request) {
        var url = new UrlEntity();
        url.setLongUrl(request.getLongUrl());
        url.setExpiresDate(request.getExpiresDate());
        url.setCreatedDate(new Date());
        var entity = urlRepository.save(url);

        return conversion.encode(entity.getId());
    }

    public String getOriginalUrl(String shortUrl) {
        var id = conversion.decode(shortUrl);
        var entity = urlRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("There is no entity with " + shortUrl));

        if (entity.getExpiresDate() != null && entity.getExpiresDate().before(new Date())){
            urlRepository.delete(entity);
            throw new EntityNotFoundException("Link expired!");
        }

        return entity.getLongUrl();
    }
}
