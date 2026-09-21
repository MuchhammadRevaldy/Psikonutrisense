<?php

namespace Database\Seeders;

use App\Models\Child;
use App\Models\GrowthRecord;
use App\Models\Mother;
use App\Models\User;
use App\Models\VaccinationRecord;
use Carbon\Carbon;
use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\Hash;

/**
 * Seeds one fully-populated "demo" account so CRUD flows (edit/delete mother
 * & child profile, growth chart history, immunization history) can be tested
 * without manually creating data through the app first.
 *
 * Run with: php artisan db:seed --class=DummyAccountSeeder
 * Login: demo@psikonutrisense.test / password123
 */
class DummyAccountSeeder extends Seeder
{
    public function run(): void
    {
        $user = User::firstOrCreate(
            ['email' => 'demo@psikonutrisense.test'],
            [
                'name' => 'Ibu Demo',
                'password' => Hash::make('password123'),
                'role' => 'ibu',
            ]
        );

        $mother = Mother::firstOrCreate(
            ['user_id' => $user->id],
            [
                'nama_ibu' => 'Siti Demo',
                'nik_ibu' => '3201234567890001',
                'tanggal_lahir_ibu' => '1995-04-12',
                'pendidikan_terakhir' => 'SMA / SMK / Sederajat',
                'pekerjaan' => 'Ibu Rumah Tangga',
                'alamat_dusun_rt_rw' => 'Dusun Mawar RT 02 RW 05',
                'no_hp_whatsapp' => '081234567890',
                'poin_ibu_aktif' => 1250,
                'lencana_aktif' => true,
            ]
        );

        $childBirthDate = Carbon::now()->subMonths(10)->startOfMonth();

        $child = Child::firstOrCreate(
            ['mother_id' => $mother->id],
            [
                'nama_balita' => 'Adi Demo',
                'tanggal_lahir_balita' => $childBirthDate->toDateString(),
                'jenis_kelamin' => 'L',
                'anak_ke' => 1,
                'jumlah_saudara' => 0,
                'bb_lahir_kg' => 3.1,
                'pb_lahir_cm' => 49.0,
                'lahir_prematur' => false,
            ]
        );

        if ($child->growthRecords()->count() === 0) {
            $growthByMonth = [
                0 => ['bb' => 3.1, 'tb' => 49.0, 'lk' => 34.0, 'lila' => 10.5],
                2 => ['bb' => 5.2, 'tb' => 56.5, 'lk' => 38.5, 'lila' => 11.8],
                4 => ['bb' => 6.4, 'tb' => 62.0, 'lk' => 40.5, 'lila' => 12.5],
                6 => ['bb' => 7.3, 'tb' => 66.5, 'lk' => 42.0, 'lila' => 13.0],
                8 => ['bb' => 8.1, 'tb' => 70.0, 'lk' => 43.2, 'lila' => 13.4],
                10 => ['bb' => 8.7, 'tb' => 73.0, 'lk' => 44.0, 'lila' => 13.8],
            ];

            foreach ($growthByMonth as $monthOffset => $measurement) {
                GrowthRecord::create([
                    'child_id' => $child->id,
                    'tanggal_penimbangan' => $childBirthDate->copy()->addMonths($monthOffset)->toDateString(),
                    'berat_badan_kg' => $measurement['bb'],
                    'tinggi_badan_cm' => $measurement['tb'],
                    'lingkar_kepala_cm' => $measurement['lk'],
                    'lila_cm' => $measurement['lila'],
                    'status_penimbangan' => $monthOffset === 0 ? 'Baru' : 'Naik',
                    'status_gizi' => 'Normal',
                    'zscore_bbu' => 0.1 * ($monthOffset % 3),
                    'zscore_tbu' => 0.05 * ($monthOffset % 4),
                ]);
            }
        }

        if ($child->vaccinationRecords()->count() === 0) {
            $ageInMonths = (int) $childBirthDate->diffInMonths(Carbon::now());

            $schedule = [
                ['jenis_imunisasi' => 'Hepatitis B (HB-0)', 'usia_bulan' => 0],
                ['jenis_imunisasi' => 'BCG & Polio 1', 'usia_bulan' => 1],
                ['jenis_imunisasi' => 'DPT-HB-Hib 1 & Polio 2', 'usia_bulan' => 2],
                ['jenis_imunisasi' => 'DPT-HB-Hib 2 & Polio 3', 'usia_bulan' => 3],
                ['jenis_imunisasi' => 'DPT-HB-Hib 3 & Polio 4 & IPV', 'usia_bulan' => 4],
                ['jenis_imunisasi' => 'Campak / MR', 'usia_bulan' => 9],
            ];

            foreach ($schedule as $item) {
                $scheduledDate = $childBirthDate->copy()->addMonths($item['usia_bulan']);
                $isDue = $item['usia_bulan'] <= $ageInMonths;

                VaccinationRecord::create([
                    'child_id' => $child->id,
                    'jenis_imunisasi' => $item['jenis_imunisasi'],
                    'tanggal_jadwal' => $scheduledDate->toDateString(),
                    'tanggal_pemberian' => $isDue ? $scheduledDate->copy()->addDays(2)->toDateString() : null,
                    'status' => $isDue ? 'Sudah' : 'Belum',
                    'alasan_belum' => null,
                ]);
            }
        }
    }
}
