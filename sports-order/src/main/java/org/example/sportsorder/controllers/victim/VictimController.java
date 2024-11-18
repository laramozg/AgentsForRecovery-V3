package org.example.sportsorder.controllers.victim;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.sportsorder.controllers.victim.dto.VictimDto;
import org.example.sportsorder.controllers.victim.dto.VictimRequest;
import org.example.sportsorder.controllers.victim.dto.VictimResponse;
import org.example.sportsorder.mappers.VictimMapper;
import org.example.sportsorder.services.VictimService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/victim")
public class VictimController {
    public static final int DEFAULT_PAGE_SIZE = 50;
    private final VictimService victimService;
    private final VictimMapper victimMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public VictimResponse createVictim(
            @Valid @RequestBody VictimRequest request) {
        return new VictimResponse(victimService.createVictim(victimMapper.convertToEntity(request)));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VictimDto getVictim(@PathVariable UUID id) {
        return victimMapper.convertToDto(victimService.find(id));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<VictimDto> getAllVictims(
            @PageableDefault(size = DEFAULT_PAGE_SIZE) Pageable pageable
    ) {
        return victimService.findAllVictims(pageable).map(victimMapper::convertToDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVictim(@PathVariable UUID id) {
        victimService.deleteVictim(id);
    }
}
