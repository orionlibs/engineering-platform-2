package com.dimi.core.data;

import com.dimi.core.exception.UncheckedException;

public class DuplicateRecordException extends UncheckedException
{
    private static final String DEFAULT_ERROR_MESSAGE = "There was an error.";


    public DuplicateRecordException(String errorMessage)
    {
        super(errorMessage);
    }


    public DuplicateRecordException(String errorMessage, Object... arguments)
    {
        super(String.format(errorMessage, arguments));
    }


    public DuplicateRecordException(Throwable cause, String errorMessage, Object... arguments)
    {
        super(String.format(errorMessage, arguments), cause);
    }


    public DuplicateRecordException(Throwable cause)
    {
        super(DEFAULT_ERROR_MESSAGE, cause);
    }
}
