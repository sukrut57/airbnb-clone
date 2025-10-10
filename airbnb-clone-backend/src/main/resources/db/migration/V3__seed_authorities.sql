UPDATE airbnb_clone.authority
SET name = 'ROLE_LANDLORD',
    modified_at = NOW()
WHERE name = 'ROLE_TENANT';