SELECT * FROM individual_mpd AS mpd JOIN (outlets AS o, alignments AS a, substances AS s)
    on mpd.outlet_id=o.outlet_id and mpd.alignment_id=a.alignment_id;