$(document).ready(function() {
    var table = $('#clusters').DataTable({
        "order": [[ 3, "desc" ]],
        "searching": false,
        "pagingType": "numbers",
        "pageLength": 25,
        "language": {
            "lengthMenu": "Wyświetl _MENU_ folderów",
            "zeroRecords": "Brak folderów",
            "info": "Wyświetlono foldery _START_ - _END_  z _MAX_",
            "emptyTable": "Brak danych",
            "loadingRecords": "Wczytywanie danych..."
        },
        "columns": [
            {"sortable" : false},
            {"sortable" : false},
            null,
            null
        ]
    })

    $('#clusters tbody').on('click', 'td span', function (event) {
        var tr = $(this).closest('tr');
        var id = event.target.id;
        var row = table.row( tr );

        if ( row.child.isShown() ) {
            row.child.hide();
            tr.removeClass('shown');
        }
        else {
            var auctionsId = "auctions" + id;
            var clusterId = "clusters/" + id;
            var auctions = row.child( format( auctionsId ) );
            auctions.show();
            var searchValue = get('value');
            var similarity = get('similarity');
            tr.addClass('shown');

            $('#'+auctionsId).DataTable( {
                "ajax": {
                    "url": clusterId + "?searchValue=" + searchValue + "&similarity=" + similarity,
                    "dataSrc": ""
                },
                "columns": [
                    {
                        "data": "photoBase64",
                        "sortable": false,
                        "render": function(data) {
                            if (data !== null) {
                                return '<img alt="Brak zdjęcia" src="'+data+'">'
                            }
                        }
                    },
                    { "data": "title" },
                    { "data": "prices.0.priceValue" },
                    { "data": "prices.1.priceValue" },
                    { "data": "sellerLogin" },
                    { "data": "endingTime" },
                    {
                        "data": "auctionUrl",
                        "sortable": false,
                        "render": function(data) {
                            if (data !== null) {
                                return '<a href="'+data+'" target="_blank">Aukcja</a>'
                            }
                        }
                    }

                ],
                "order": [[ 3, "asc" ]],
                "searching": false,
                "pagingType": "numbers",
                "info" : false,
                "language": {
                    "lengthMenu": "Wyświetl _MENU_ aukcji w folderze",
                    "zeroRecords": "Folder jest pusty",
                    "emptyTable": "Brak danych",
                    "loadingRecords": "Wczytywanie danych..."
                }
            } );

        }
    } );
} );

function format ( auctionsId ) {
    return '<table id='+auctionsId+' class="display" cellspacing="0" width="100%">\n' +
        '<thead>\n' +
        '    <tr>\n' +
        '        <th>Zdjęcie aukcji</th>\n' +
        '        <th>Nazwa aukcji</th>\n' +
        '        <th>Cena bez dostawy</th>\n' +
        '        <th>Cena z dostawą</th>\n' +
        '        <th>Sprzedawca</th>\n' +
        '        <th>Czas do końca</th>\n' +
        '        <th></th>\n' +
        '    </tr>' +
        '</thead>\n' +
        '</table>';
}

function get(value)
{
    var url_string = window.location.href;
    var url = new URL(url_string);
    var result = url.searchParams.get(value);
    return result;
}