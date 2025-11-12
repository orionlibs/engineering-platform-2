package com.dimi.core;

import com.dimi.core.exception.AError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
public abstract class ServiceOperationResult
{
    private AError error;
}
